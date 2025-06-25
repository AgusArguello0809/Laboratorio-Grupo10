package FitStore.TpoGrupo10.unit.persistence.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;

class ItemCarritoEmbeddableTest {

    @Test
    void testGettersAndSetters() {
        ItemCarritoEmbeddable item = new ItemCarritoEmbeddable();

        item.setProductoId(10L);
        item.setCantidad(2);
        item.setPrecioUnitario(100.0);
        item.setSubTotal(200.0);

        assertEquals(10L, item.getProductoId());
        assertEquals(2, item.getCantidad());
        assertEquals(100.0, item.getPrecioUnitario());
        assertEquals(200.0, item.getSubTotal());
    }

    @Test
    void testToString() {
        ItemCarritoEmbeddable item = new ItemCarritoEmbeddable();
        item.setProductoId(1L);
        item.setCantidad(3);
        item.setPrecioUnitario(50.0);
        item.setSubTotal(150.0);

        String expected = "ItemCarritoEmbeddable{productoId=1, cantidad=3, precioUnitario=50.0, subTotal=150.0}";
        assertEquals(expected, item.toString());
    }
}