package FitStore.TpoGrupo10.unit.models;

import FitStore.TpoGrupo10.models.ItemOrdenModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemOrdenModelTest {

    @Test
    void testItemOrdenModel() {
        ItemOrdenModel item = new ItemOrdenModel();
        item.setProductoId(1L);
        item.setNombreProducto("Producto Test");
        item.setCantidad(2);
        item.setPrecioUnitario(150.0);
        item.setSubTotal(300.0);

        assertEquals(1L, item.getProductoId());
        assertEquals("Producto Test", item.getNombreProducto());
        assertEquals(2, item.getCantidad());
        assertEquals(150.0, item.getPrecioUnitario());
        assertEquals(300.0, item.getSubTotal());
        assertTrue(item.toString().contains("Producto Test"));
    }
}