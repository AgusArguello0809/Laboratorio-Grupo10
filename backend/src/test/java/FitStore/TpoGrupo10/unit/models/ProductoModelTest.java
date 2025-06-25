package FitStore.TpoGrupo10.unit.models;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductoModelTest {

    @Test
    void testGettersAndSetters() {
        ProductoModel producto = new ProductoModel();

        CategoriaModel categoria = new CategoriaModel();
        categoria.setId(1L);
        categoria.setNombre("Suplementos");

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(2L);
        usuario.setUsername("fitUser");

        producto.setId(10L);
        producto.setTitle("Proteína Whey");
        producto.setDescription("Suplemento de proteína");
        producto.setStock(20);
        producto.setPrice(5000.0);
        producto.setCategory(categoria);
        producto.setOwner(usuario);
        producto.setImages(List.of("img1.jpg", "img2.jpg"));

        assertEquals(10L, producto.getId());
        assertEquals("Proteína Whey", producto.getTitle());
        assertEquals("Suplemento de proteína", producto.getDescription());
        assertEquals(20, producto.getStock());
        assertEquals(5000.0, producto.getPrice());
        assertEquals(categoria, producto.getCategory());
        assertEquals(usuario, producto.getOwner());
        assertEquals(2, producto.getImages().size());
    }

    @Test
    void testUpdateFrom() {
        ProductoModel original = new ProductoModel();
        ProductoModel nuevo = new ProductoModel();

        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre("Accesorios");

        nuevo.setTitle("Cinturón de fuerza");
        nuevo.setDescription("Para levantamiento de pesas");
        nuevo.setStock(5);
        nuevo.setPrice(1500.0);
        nuevo.setCategory(categoria);
        nuevo.setImages(List.of("cinturon.jpg"));

        original.updateFrom(nuevo);

        assertEquals("Cinturón de fuerza", original.getTitle());
        assertEquals("Para levantamiento de pesas", original.getDescription());
        assertEquals(5, original.getStock());
        assertEquals(1500.0, original.getPrice());
        assertEquals("Accesorios", original.getCategory().getNombre());
        assertEquals(1, original.getImages().size());
    }

    @Test
    void testToStringCompleto() {
        ProductoModel producto = new ProductoModel();

        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre("Máquinas");

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(42L);

        producto.setId(7L);
        producto.setTitle("Bicicleta fija");
        producto.setStock(3);
        producto.setPrice(120000.0);
        producto.setCategory(categoria);
        producto.setOwner(usuario);
        producto.setImages(List.of("img1.png", "img2.png", "img3.png"));

        String toString = producto.toString();

        assertTrue(toString.contains("id=7"));
        assertTrue(toString.contains("title='Bicicleta fija'"));
        assertTrue(toString.contains("stock=3"));
        assertTrue(toString.contains("price=120000.0"));
        assertTrue(toString.contains("category=Máquinas"));
        assertTrue(toString.contains("ownerId=42"));
        assertTrue(toString.contains("3 images"));
    }
}
