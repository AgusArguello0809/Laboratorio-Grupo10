package FitStore.TpoGrupo10.unit.persistence.entities;

import FitStore.TpoGrupo10.persistence.entities.ItemOrdenEmbeddable;
import FitStore.TpoGrupo10.persistence.entities.OrdenEntity;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class OrdenEntityTest {

    @Test
    void testOrdenEntity() {
        ItemOrdenEmbeddable item = new ItemOrdenEmbeddable();
        item.setProductoId(3L);
        item.setNombreProducto("Orden Embebida");
        item.setCantidad(1);
        item.setPrecioUnitario(500.0);
        item.setSubTotal(500.0);

        OrdenEntity orden = new OrdenEntity();
        orden.setId(20L);
        orden.setTotal(500.0);
        orden.setFecha(LocalDateTime.now());
        orden.setProductos(Collections.singletonList(item));

        assertEquals(20L, orden.getId());
        assertEquals(500.0, orden.getTotal());
        assertEquals(1, orden.getProductos().size());
        assertNotNull(orden.getFecha());
        assertTrue(orden.toString().contains("OrdenEntity"));
    }
}
