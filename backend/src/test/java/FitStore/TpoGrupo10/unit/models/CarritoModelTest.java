package FitStore.TpoGrupo10.unit.models;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarritoModelTest {

    private CarritoModel carrito;
    private UsuarioModel usuario;
    private ItemCarritoModel item1;
    private ItemCarritoModel item2;

    @BeforeEach
    void setUp() {
        carrito = new CarritoModel();
        usuario = new UsuarioModel();
        usuario.setId(99L);

        item1 = new ItemCarritoModel();
        item1.setProductoId(1L);
        item1.setCantidad(2);
        item1.setPrecioUnitario(100.0);
        item1.setSubTotal(200.0);

        item2 = new ItemCarritoModel();
        item2.setProductoId(2L);
        item2.setCantidad(1);
        item2.setPrecioUnitario(150.0);
        item2.setSubTotal(150.0);
    }

    @Test
    void testGettersAndSetters() {
        carrito.setId(1L);
        carrito.setOwner(usuario);
        carrito.setProductos(List.of(item1, item2));
        carrito.setTotal(350.0);

        assertEquals(1L, carrito.getId());
        assertEquals(usuario, carrito.getOwner());
        assertEquals(2, carrito.getProductos().size());
        assertEquals(350.0, carrito.getTotal());
    }

    @Test
    void testToStringConDatos() {
        carrito.setId(1L);
        carrito.setOwner(usuario);
        carrito.setProductos(List.of(item1, item2));
        carrito.setTotal(350.0);

        String toString = carrito.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("owner=99"));
        assertTrue(toString.contains("2 items"));
        assertTrue(toString.contains("total=350.0"));
    }

    @Test
    void testToStringSinOwnerNiProductos() {
        carrito.setId(2L);
        carrito.setTotal(0.0);

        String toString = carrito.toString();
        assertTrue(toString.contains("id=2"));
        assertTrue(toString.contains("owner=null"));
        assertTrue(toString.contains("0 items"));
        assertTrue(toString.contains("total=0.0"));
    }
}
