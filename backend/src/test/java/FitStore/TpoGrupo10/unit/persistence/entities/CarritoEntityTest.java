package FitStore.TpoGrupo10.unit.persistence.entities;

import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarritoEntityTest {

    private CarritoEntity carrito;
    private UsuarioEntity usuario;
    private ItemCarritoEmbeddable item1;
    private ItemCarritoEmbeddable item2;

    @BeforeEach
    void setUp() {
        carrito = new CarritoEntity();
        usuario = new UsuarioEntity();
        usuario.setId(42L);

        item1 = new ItemCarritoEmbeddable();
        item1.setProductoId(1L);
        item1.setCantidad(3);
        item1.setPrecioUnitario(100.0);
        item1.setSubTotal(300.0);

        item2 = new ItemCarritoEmbeddable();
        item2.setProductoId(2L);
        item2.setCantidad(2);
        item2.setPrecioUnitario(150.0);
        item2.setSubTotal(300.0);
    }

    @Test
    void testGettersAndSetters() {
        carrito.setId(5L);
        carrito.setOwner(usuario);
        carrito.setProductos(List.of(item1, item2));
        carrito.setTotal(600.0);

        assertEquals(5L, carrito.getId());
        assertEquals(usuario, carrito.getOwner());
        assertEquals(2, carrito.getProductos().size());
        assertEquals(600.0, carrito.getTotal());
    }

    @Test
    void testToStringConDatos() {
        carrito.setId(10L);
        carrito.setOwner(usuario);
        carrito.setProductos(List.of(item1, item2));
        carrito.setTotal(600.0);

        String str = carrito.toString();
        assertTrue(str.contains("id=10"));
        assertTrue(str.contains("ownerId=42"));
        assertTrue(str.contains("2 items"));
        assertTrue(str.contains("total=600.0"));
    }

    @Test
    void testToStringSinOwnerNiProductos() {
        carrito.setId(99L);
        carrito.setTotal(0.0);

        String str = carrito.toString();
        assertTrue(str.contains("id=99"));
        assertTrue(str.contains("ownerId=null"));
        assertTrue(str.contains("0 items"));
        assertTrue(str.contains("total=0.0"));
    }
}
