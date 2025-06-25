package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.service.OrdenService;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.*;
import FitStore.TpoGrupo10.persistence.repositories.CarritoRepository;
import FitStore.TpoGrupo10.business.service.CarritoService;
import FitStore.TpoGrupo10.business.service.ProductoService;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.security.exception.SecurityException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import FitStore.TpoGrupo10.business.exception.BusinessException;

import java.util.*;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;
    private final ProductoService productoService;
    private final UsuarioRepository usuarioRepository;
    private final OrdenService ordenService;

    public CarritoServiceImpl(CarritoRepository repository, ProductoService productoService, UsuarioRepository usuarioRepository, OrdenService ordenService) {
        this.repository = repository;
        this.productoService = productoService;
        this.usuarioRepository = usuarioRepository;
        this.ordenService = ordenService;
    }

    private UsuarioModel getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
    }

    @Override
    public CarritoModel getCarritoByOwnerId() {
        UsuarioModel usuario = getAuthenticatedUser();

        return repository.findByOwnerId(usuario.getId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));
    }

    @Override
    public CarritoModel save(CarritoModel model) {
        return repository.save(model);
    }

    @Override
    public void deleteCarrito(Long id) {
        repository.findById(id).orElseThrow(() ->
                        new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));

        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin) {
            throw new SecurityException(ErrorCodeEnum.ACCESS_DENIED.getMessage(), ErrorCodeEnum.ACCESS_DENIED);
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteProductoCarrito(Long id, Long productoId) {
        CarritoModel carrito = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));

        verificarAutorizacion(carrito);

        carrito.getProductos().removeIf(item -> item.getProductoId().equals(productoId));
        carrito.setTotal(calcularTotal(carrito.getProductos()));
        repository.save(carrito);
    }

    @Override
    public CarritoModel incrementarCantidad(Long carritoId, Long productoId) {
        CarritoModel carrito = repository.findById(carritoId)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));

        verificarAutorizacion(carrito);

        ProductoModel producto = productoService.findById(productoId);

        for (ItemCarritoModel item : carrito.getProductos()) {
            if (item.getProductoId().equals(productoId)) {
                int nuevaCantidad = item.getCantidad() + 1;
                if (producto.getStock() < nuevaCantidad) {
                    throw new BusinessException(ErrorCodeEnum.STOCK_INSUFICIENTE.getMessage(), ErrorCodeEnum.STOCK_INSUFICIENTE);
                }
                item.setCantidad(nuevaCantidad);
                recalcularSubtotal(item);
                break;
            }
        }

        carrito.setTotal(calcularTotal(carrito.getProductos()));
        return repository.save(carrito);
    }

    @Override
    public CarritoModel disminuirCantidad(Long carritoId, Long productoId) {
        CarritoModel carrito = repository.findById(carritoId)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));

        verificarAutorizacion(carrito);

        for (ItemCarritoModel item : carrito.getProductos()) {
            if (item.getProductoId().equals(productoId)) {
                item.setCantidad(item.getCantidad() - 1);
                recalcularSubtotal(item);
                break;
            }
        }

        carrito.setTotal(calcularTotal(carrito.getProductos()));
        return repository.save(carrito);
    }

    @Override
    public CarritoModel actualizarCantidad(Long idCarrito, Long idProducto, int nuevaCantidad) {
        if (nuevaCantidad < 1 || nuevaCantidad > 9999) {
            throw new BusinessException(
                    ErrorCodeEnum.CANTIDAD_INVALIDA.getMessage(),
                    ErrorCodeEnum.CANTIDAD_INVALIDA
            );
        }

        CarritoModel carrito = repository.findById(idCarrito)
                .orElseThrow(() -> new BusinessException(
                        ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(),
                        ErrorCodeEnum.CARRITO_NO_ENCONTRADO
                ));

        verificarAutorizacion(carrito);

        ItemCarritoModel item = carrito.getProductos().stream()
                .filter(p -> Objects.equals(p.getProductoId(), idProducto))
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO.getMessage(),
                        ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO
                ));

        ProductoModel producto = productoService.findById(idProducto);

        if (producto.getOwner().getId().equals(carrito.getOwner().getId())) {
            throw new BusinessException(
                    ErrorCodeEnum.PROPIO_PRODUCTO.getMessage(),
                    ErrorCodeEnum.PROPIO_PRODUCTO
            );
        }

        if (producto.getStock() < nuevaCantidad) {
            throw new BusinessException(
                    ErrorCodeEnum.STOCK_INSUFICIENTE.getMessage(),
                    ErrorCodeEnum.STOCK_INSUFICIENTE
            );
        }

        item.setCantidad(nuevaCantidad);
        actualizarPrecioYSubtotal(item, producto);

        carrito.setTotal(calcularTotal(carrito.getProductos()));
        return repository.save(carrito);
    }

    @Override
    public void vaciarCarrito(Long id) {
        CarritoModel carrito = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));

        verificarAutorizacion(carrito);

        carrito.setProductos(new ArrayList<>());
        carrito.setTotal(0);
        repository.save(carrito);
    }

    @Override
    public CarritoModel agregarProducto(Long productId, int cant) {
        UsuarioModel usuario = getAuthenticatedUser();

        CarritoModel carrito = repository.findByOwnerId(usuario.getId())
                .orElseGet(() -> {
                    CarritoModel nuevo = new CarritoModel();
                    nuevo.setOwner(usuario);
                    nuevo.setProductos(new ArrayList<>());
                    return nuevo;
                });

        ProductoModel producto = productoService.findById(productId);
        List<ItemCarritoModel> productos = carrito.getProductos();

        Optional<ItemCarritoModel> itemExistente = productos.stream()
                .filter(p -> Objects.equals(productId, p.getProductoId()))
                .findFirst();

        int cantidadTotal = cant;
        if (itemExistente.isPresent()) {
            cantidadTotal += itemExistente.get().getCantidad();
        }

        if (producto.getStock() < cantidadTotal) {
            throw new BusinessException(ErrorCodeEnum.STOCK_INSUFICIENTE.getMessage() + "  para el producto con ID: " + productId, ErrorCodeEnum.STOCK_INSUFICIENTE);
        }

        if (itemExistente.isPresent()) {
            ItemCarritoModel existente = itemExistente.get();
            existente.setCantidad(cantidadTotal);
            recalcularSubtotal(existente);
        } else {
            ItemCarritoModel nuevoItem = new ItemCarritoModel();
            nuevoItem.setProductoId(productId);
            nuevoItem.setCantidad(cant);
            nuevoItem.setPrecioUnitario(producto.getPrice());
            recalcularSubtotal(nuevoItem);
            productos.add(nuevoItem);
        }

        carrito.setProductos(productos);
        carrito.setTotal(calcularTotal(productos));
        return repository.save(carrito);
    }

    @Override
    public OrdenModel checkout(Long id) {
        CarritoModel carrito = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CARRITO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.CARRITO_NO_ENCONTRADO));

        verificarAutorizacion(carrito);

        List<ProductoModel> productosActuales = new ArrayList<>();

        for (ItemCarritoModel item : carrito.getProductos()) {
            ProductoModel producto = productoService.findById(item.getProductoId());

            if (producto.getStock() < item.getCantidad()) {
                throw new BusinessException(ErrorCodeEnum.STOCK_INSUFICIENTE.getMessage() + " para el producto con ID: " + item.getProductoId(), ErrorCodeEnum.STOCK_INSUFICIENTE);
            }

            if (producto.getOwner().getId().equals(carrito.getOwner().getId())) {
                throw new BusinessException(ErrorCodeEnum.PROPIO_PRODUCTO.getMessage(), ErrorCodeEnum.PROPIO_PRODUCTO);
            }

            productoService.actualizarPrecioYStock(
                    producto.getId(),
                    producto.getPrice(),
                    producto.getStock() - item.getCantidad()
            );

            actualizarPrecioYSubtotal(item, producto);
            productosActuales.add(producto);
        }

        // Crear y guardar la orden
        OrdenModel ordenFromCarrito = OrdenModel.fromCarrito(carrito, productosActuales);
        OrdenModel orden = ordenService.save(ordenFromCarrito);

        // Vaciar carrito
        carrito.setProductos(new ArrayList<>());
        carrito.setTotal(0);
        repository.save(carrito);
        return orden;
    }

    private void verificarAutorizacion(CarritoModel carrito) {
        UsuarioModel actual = getAuthenticatedUser();
        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin && !Objects.equals(carrito.getOwner().getId(), actual.getId())) {
            throw new SecurityException(ErrorCodeEnum.ACCESS_DENIED.getMessage(), ErrorCodeEnum.ACCESS_DENIED);
        }
    }

    private void recalcularSubtotal(ItemCarritoModel item) {
        item.setSubTotal(item.getCantidad() * item.getPrecioUnitario());
    }

    private double calcularTotal(List<ItemCarritoModel> productos) {
        return productos.stream().mapToDouble(ItemCarritoModel::getSubTotal).sum();
    }

    private void actualizarPrecioYSubtotal(ItemCarritoModel item, ProductoModel producto) {
        item.setPrecioUnitario(producto.getPrice());
        recalcularSubtotal(item);
    }
}
