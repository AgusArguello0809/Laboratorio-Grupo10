package FitStore.TpoGrupo10.business.service;


import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.OrdenModel;

public interface CarritoService {
    CarritoModel getCarritoByOwnerId();

    CarritoModel save(CarritoModel model);

    void deleteCarrito(Long id);

    void deleteProductoCarrito(Long id, Long productoId);

    CarritoModel incrementarCantidad(Long carritoId, Long productoId);

    CarritoModel disminuirCantidad(Long carritoId, Long productoId);

    CarritoModel actualizarCantidad(Long idCarrito, Long idProducto, int nuevaCantidad);

    void vaciarCarrito(Long id);

    CarritoModel agregarProducto(Long productId, int cant);

    OrdenModel checkout(Long id);
}
