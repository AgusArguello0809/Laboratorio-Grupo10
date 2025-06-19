package FitStore.TpoGrupo10.business.service;

import FitStore.TpoGrupo10.models.CarritoModel;

import java.util.Optional;

public interface CarritoService {
    Optional<CarritoModel> getCarritoByOwnerId(Long ownerId);
    CarritoModel agregarProducto(Long ownerId, Long productId, int cant);
    CarritoModel save(CarritoModel model);
    void deleteCarrito(Long id);
    void deleteCarritoProducto(Long carritoId, Long productoId);
    CarritoModel incrementarCantidad(Long carritoId, Long productoId);
    CarritoModel disminuirCantidad(Long carritoId, Long productoId);
    void vaciarCarrito(Long id);
    CarritoModel checkout(Long id);
}
