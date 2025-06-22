package FitStore.TpoGrupo10.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.models.CategoriaModel;

class CategoriaModelTest {

    @Test
    void testGettersAndSetters() {
        CategoriaModel categoria = new CategoriaModel();

        categoria.setId(10L);
        categoria.setNombre("Suplementos");

        assertEquals(10L, categoria.getId());
        assertEquals("Suplementos", categoria.getNombre());
    }

    @Test
    void testToString() {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setId(5L);
        categoria.setNombre("Ropa deportiva");

        String result = categoria.toString();

        assertTrue(result.contains("id=5"));
        assertTrue(result.contains("nombre='Ropa deportiva'"));
    }
}
