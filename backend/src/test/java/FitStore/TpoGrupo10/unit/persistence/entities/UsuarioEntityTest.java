package FitStore.TpoGrupo10.unit.persistence.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import FitStore.TpoGrupo10.persistence.entities.enums.Role;

class UsuarioEntityTest {

    @Test
    void testGettersAndSetters() {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setId(1L);
        usuario.setUsername("lucascasas");
        usuario.setName("Lucas");
        usuario.setLastName("Casás");
        usuario.setEmail("lucas@email.com");
        usuario.setPassword("secure123");
        usuario.setRole(Role.ADMIN);

        assertEquals(1L, usuario.getId());
        assertEquals("lucascasas", usuario.getUsername());
        assertEquals("Lucas", usuario.getName());
        assertEquals("Casás", usuario.getLastName());
        assertEquals("lucas@email.com", usuario.getEmail());
        assertEquals("secure123", usuario.getPassword());
        assertEquals(Role.ADMIN, usuario.getRole());
    }
}
