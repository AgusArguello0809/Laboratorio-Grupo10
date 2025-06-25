package FitStore.TpoGrupo10.unit.models;

import FitStore.TpoGrupo10.models.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrdenModelTest {

    @Test
    void testOrdenModel() {
        ItemOrdenModel item = new ItemOrdenModel();
        item.setProductoId(1L);
        item.setNombreProducto("Producto Test");
        item.setCantidad(2);
        item.setPrecioUnitario(150.0);
        item.setSubTotal(300.0);

        OrdenModel orden = new OrdenModel();
        orden.setId(10L);
        orden.setTotal(300.0);
        orden.setFecha(LocalDateTime.now());
        orden.setProductos(Collections.singletonList(item));

        assertEquals(10L, orden.getId());
        assertEquals(300.0, orden.getTotal());
        assertEquals(1, orden.getProductos().size());
        assertNotNull(orden.getFecha());
        assertTrue(orden.toString().contains("10"));
    }

    @Test
    void testFromCarritoCreatesValidOrden() {
        // Setup carrito
        CarritoModel carrito = new CarritoModel();
        ItemCarritoModel item = new ItemCarritoModel();
        item.setProductoId(1L);
        item.setCantidad(2);
        item.setPrecioUnitario(150.0);
        item.setSubTotal(300.0);
        carrito.setProductos(List.of(item));
        carrito.setTotal(300.0);

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(42L);
        carrito.setOwner(usuario);

        // ProductoModel correspondiente
        ProductoModel producto = new ProductoModel();
        producto.setId(1L);
        producto.setTitle("Producto Test");

        List<ProductoModel> productosActuales = List.of(producto);

        OrdenModel orden = OrdenModel.fromCarrito(carrito, productosActuales);

        // Verificaciones
        assertEquals(42L, orden.getComprador().getId());
        assertEquals(1, orden.getProductos().size());

        ItemOrdenModel itemOrden = orden.getProductos().get(0);
        assertEquals(1L, itemOrden.getProductoId());
        assertEquals("Producto Test", itemOrden.getNombreProducto());
        assertEquals(2, itemOrden.getCantidad());
        assertEquals(150.0, itemOrden.getPrecioUnitario());
        assertEquals(300.0, itemOrden.getSubTotal());

        assertEquals(300.0, orden.getTotal());
        assertNotNull(orden.getFecha());
    }

    @Test
    void testFromCarritoThrowsIfProductMissing() {
        // Setup carrito con un ítem
        CarritoModel carrito = new CarritoModel();
        ItemCarritoModel item = new ItemCarritoModel();
        item.setProductoId(99L);
        item.setCantidad(1);
        item.setPrecioUnitario(100.0);
        item.setSubTotal(100.0);
        carrito.setProductos(List.of(item));
        carrito.setTotal(100.0);
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1L);
        carrito.setOwner(usuario);

        // productosActuales no incluye el producto con ID 99L
        List<ProductoModel> productosActuales = List.of(); // lista vacía

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            OrdenModel.fromCarrito(carrito, productosActuales);
        });

        assertTrue(ex.getMessage().contains("No se encontró el producto con ID 99"));
    }

}
