package FitStore.TpoGrupo10.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.enums.Role;

class UsuarioModelTest {

    @Test
    void testGettersAndSetters() {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setId(1L);
        usuario.setUsername("agus");
        usuario.setName("Agustin");
        usuario.setLastName("Pérez");
        usuario.setEmail("agus@example.com");
        usuario.setPassword("secreto123");
        usuario.setRole(Role.CLIENTE);

        assertEquals(1L, usuario.getId());
        assertEquals("agus", usuario.getUsername());
        assertEquals("Agustin", usuario.getName());
        assertEquals("Pérez", usuario.getLastName());
        assertEquals("agus@example.com", usuario.getEmail());
        assertEquals("secreto123", usuario.getPassword());
        assertEquals(Role.CLIENTE, usuario.getRole());
    }

    @Test
    void testToString() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(2L);
        usuario.setUsername("lucas");
        usuario.setEmail("lucas@example.com");
        usuario.setRole(Role.ADMIN);

        String resultado = usuario.toString();

        assertTrue(resultado.contains("id=2"));
        assertTrue(resultado.contains("username='lucas'"));
        assertTrue(resultado.contains("email='lucas@example.com'"));
        assertTrue(resultado.contains("role=ADMIN"));
    }
}
