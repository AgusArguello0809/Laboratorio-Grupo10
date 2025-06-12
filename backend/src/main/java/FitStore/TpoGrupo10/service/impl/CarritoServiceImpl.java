package FitStore.TpoGrupo10.service.impl;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.*;
import FitStore.TpoGrupo10.persistence.repositories.CarritoRepository;
import FitStore.TpoGrupo10.service.CarritoService;
import FitStore.TpoGrupo10.service.ProductoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        List<ItemCarritoModel> productos = carrito.getProductos();
        productos.removeIf(item -> item.getProductoId().equals(productoId));

        carrito.setProductos(productos);
        repository.save(carrito);
    }

    @Override
    public CarritoModel incrementarCantidad(Long carritoId, Long productoId) {
        CarritoModel carrito = repository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        for (ItemCarritoModel item : carrito.getProductos()) {
            if (item.getProductoId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + 1);
                break;
            }
        }

        return repository.save(carrito);
    }

    public CarritoModel addProduct(Long ownerId, Long productId, int cant) {

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

        Optional<ItemCarritoModel> itemExistente = productos.stream()
                .filter(p -> Objects.equals(productId, p.getProductoId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarritoModel existente = itemExistente.get();
            existente.setCantidad(existente.getCantidad() + cant);
        } else {
            ItemCarritoModel nuevoItem = new ItemCarritoModel();
            nuevoItem.setProductoId(productId);
            nuevoItem.setCantidad(cant);

            ProductoModel producto = productoService.findById(productId);
            nuevoItem.setPrecioUnitario(producto.getPrice());

            productos.add(nuevoItem);
        }

        carrito.setProductos(productos);

        BigDecimal total = productos.stream()
                .map(item -> BigDecimal.valueOf(item.getPrecioUnitario())
                        .multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carrito.setTotal(total.setScale(2, RoundingMode.HALF_UP).doubleValue());

        return repository.save(carrito);
    }
}