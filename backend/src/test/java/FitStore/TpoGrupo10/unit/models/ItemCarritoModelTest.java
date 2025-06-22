package FitStore.TpoGrupo10.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.models.ItemCarritoModel;

class ItemCarritoModelTest {

    @Test
    void testGettersAndSetters() {
        ItemCarritoModel item = new ItemCarritoModel();

        item.setProductoId(101L);
        item.setCantidad(3);
        item.setPrecioUnitario(500.0);
        item.setSubTotal(1500.0);

        assertEquals(101L, item.getProductoId());
        assertEquals(3, item.getCantidad());
        assertEquals(500.0, item.getPrecioUnitario());
        assertEquals(1500.0, item.getSubTotal());
    }

    @Test
    void testToString() {
        ItemCarritoModel item = new ItemCarritoModel();
        item.setProductoId(202L);
        item.setCantidad(2);
        item.setPrecioUnitario(1000.0);
        item.setSubTotal(2000.0);

        String result = item.toString();

        assertTrue(result.contains("productoId=202"));
        assertTrue(result.contains("cantidad=2"));
        assertTrue(result.contains("precioUnitario=1000.0"));
        assertTrue(result.contains("subTotal=2000.0"));
    }
}

