package FitStore.TpoGrupo10.unit.persistence.entities;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;

class ProductoEntityTest {

    @Test
    void testGettersAndSetters() {
        ProductoEntity producto = new ProductoEntity();

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1L);
        categoria.setNombre("Proteínas");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(10L);

        producto.setId(100L);
        producto.setTitle("Whey Protein");
        producto.setDescription("Suplemento de proteína");
        producto.setStock(50);
        producto.setPrice(12000.0);
        producto.setCategory(categoria);
        producto.setOwner(usuario);
        producto.setImages(List.of("img1.jpg", "img2.jpg"));

        assertEquals(100L, producto.getId());
        assertEquals("Whey Protein", producto.getTitle());
        assertEquals("Suplemento de proteína", producto.getDescription());
        assertEquals(50, producto.getStock());
        assertEquals(12000.0, producto.getPrice());
        assertEquals(categoria, producto.getCategory());
        assertEquals(usuario, producto.getOwner());
        assertEquals(2, producto.getImages().size());
        assertEquals("img1.jpg", producto.getImages().get(0));
    }
}
