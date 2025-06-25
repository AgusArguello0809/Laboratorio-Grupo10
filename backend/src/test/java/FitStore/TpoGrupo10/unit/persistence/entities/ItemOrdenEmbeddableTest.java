package FitStore.TpoGrupo10.unit.persistence.entities;

import FitStore.TpoGrupo10.persistence.entities.ItemOrdenEmbeddable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemOrdenEmbeddableTest {

    @Test
    void testItemOrdenEmbeddable() {
        ItemOrdenEmbeddable item = new ItemOrdenEmbeddable();
        item.setProductoId(2L);
        item.setNombreProducto("Embebido");
        item.setCantidad(3);
        item.setPrecioUnitario(100.0);
        item.setSubTotal(300.0);

        assertEquals(2L, item.getProductoId());
        assertEquals("Embebido", item.getNombreProducto());
        assertEquals(3, item.getCantidad());
        assertEquals(100.0, item.getPrecioUnitario());
        assertEquals(300.0, item.getSubTotal());
        assertTrue(item.toString().contains("Embebido"));
    }
}
