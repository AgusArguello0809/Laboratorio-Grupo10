package FitStore.TpoGrupo10.unit.persistence.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;

class CategoriaEntityTest {

    @Test
    void testGettersAndSetters() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1L);
        categoria.setNombre("Suplementos");

        assertEquals(1L, categoria.getId());
        assertEquals("Suplementos", categoria.getNombre());
    }

    @Test
    void testToString() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(2L);
        categoria.setNombre("Accesorios");

        String str = categoria.toString();
        assertTrue(str.contains("id=2"));
        assertTrue(str.contains("nombre='Accesorios'"));
    }
}
