package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.exceptions.enums.ErrorCode;
import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.CarritoRepository;
import FitStore.TpoGrupo10.business.service.CarritoService;
import FitStore.TpoGrupo10.business.service.ProductoService;
import org.springframework.stereotype.Service;
import FitStore.TpoGrupo10.business.exception.BusinessException;

import java.io.IOException;
import java.util.*;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;
    private final ProductoService productoService;

    public CarritoServiceImpl(CarritoRepository repository, ProductoService productoService) {
        this.repository = repository;
        this.productoService = productoService;
    }

    @Override
    public Optional<CarritoModel> getCarritoByOwnerId(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Override
    public CarritoModel save(CarritoModel model) {
        return repository.save(model);
    }

    @Override
    public void deleteCarrito(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteCarritoProducto(Long id, Long productoId) {
        CarritoModel carrito = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Carrito no encontrado", ErrorCode.CARRITO_NO_ENCONTRADO));

        List<ItemCarritoModel> productos = carrito.getProductos();
        productos.removeIf(item -> item.getProductoId().equals(productoId));

        carrito.setProductos(productos);
        carrito.setTotal(calcularTotal(productos));
        repository.save(carrito);
    }

    @Override
    public CarritoModel incrementarCantidad(Long carritoId, Long productoId) {
        CarritoModel carrito = repository.findById(carritoId)
                .orElseThrow(() -> new BusinessException("Carrito no encontrado", ErrorCode.CARRITO_NO_ENCONTRADO));

        ProductoModel producto = productoService.findById(productoId);

        for (ItemCarritoModel item : carrito.getProductos()) {
            if (item.getProductoId().equals(productoId)) {
                int nuevaCantidad = item.getCantidad() + 1;
                if (producto.getStock() < nuevaCantidad) {
                    throw new BusinessException("Stock insuficiente", ErrorCode.STOCK_INSUFICIENTE);
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
                .orElseThrow(() -> new BusinessException("Carrito no encontrado", ErrorCode.CARRITO_NO_ENCONTRADO));

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
    public void vaciarCarrito(Long id) {
        CarritoModel carrito = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Carrito no encontrado", ErrorCode.CARRITO_NO_ENCONTRADO));

        carrito.setProductos(new ArrayList<>());
        carrito.setTotal(0);
        repository.save(carrito);
    }

    @Override
    public CarritoModel agregarProducto(Long ownerId, Long productId, int cant) {
        CarritoModel carrito = repository.findByOwnerId(ownerId)
                .orElseGet(() -> {
                    CarritoModel nuevo = new CarritoModel();
                    UsuarioModel usuario = new UsuarioModel();
                    usuario.setId(ownerId);
                    nuevo.setOwner(usuario);
                    nuevo.setProductos(new ArrayList<>());
                    return nuevo;
                });

        List<ItemCarritoModel> productos = carrito.getProductos();
        ProductoModel producto = productoService.findById(productId);

        Optional<ItemCarritoModel> itemExistente = productos.stream()
                .filter(p -> Objects.equals(productId, p.getProductoId()))
                .findFirst();

        int cantidadTotal = cant;

        if (itemExistente.isPresent()) {
            cantidadTotal += itemExistente.get().getCantidad();
        }

        if (producto.getStock() < cantidadTotal) {
            throw new BusinessException("Stock insuficiente para el producto con ID: " + productId, ErrorCode.STOCK_INSUFICIENTE);
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
    public CarritoModel checkout(Long id) {
            CarritoModel carrito = repository.findById(id)
                    .orElseThrow(() -> new BusinessException("Carrito no encontrado", ErrorCode.CARRITO_NO_ENCONTRADO));

            double totalCalculado = 0.0;

            for (ItemCarritoModel item : carrito.getProductos()) {
                ProductoModel producto = productoService.findById(item.getProductoId());

                if (producto.getStock() < item.getCantidad()) {
                    throw new BusinessException("Stock insuficiente para el producto con ID: " + item.getProductoId(), ErrorCode.STOCK_INSUFICIENTE);
                }

                int nuevoStock = producto.getStock() - item.getCantidad();
                double precioActualizado = producto.getPrice();

                productoService.actualizarPrecioYStock(producto.getId(), precioActualizado, nuevoStock);

                actualizarPrecioYSubtotal(item, producto);
                totalCalculado += item.getSubTotal();
            }

            carrito.setTotal(totalCalculado);
            carrito.setProductos(new ArrayList<>());
            carrito.setTotal(0);


        return repository.save(carrito);
    }

    private void recalcularSubtotal(ItemCarritoModel item) {
        item.setSubTotal(item.getCantidad() * item.getPrecioUnitario());
    }

    private double calcularTotal(List<ItemCarritoModel> productos) {
        return productos.stream()
                .mapToDouble(ItemCarritoModel::getSubTotal)
                .sum();
    }

    private void actualizarPrecioYSubtotal(ItemCarritoModel item, ProductoModel producto) {
        item.setPrecioUnitario(producto.getPrice());
        recalcularSubtotal(item);
    }
}
