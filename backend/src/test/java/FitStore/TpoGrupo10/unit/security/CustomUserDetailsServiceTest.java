package FitStore.TpoGrupo10.unit.security;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.security.CustomUserDetailsService;
import FitStore.TpoGrupo10.persistence.entities.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private UsuarioRepository usuarioRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        userDetailsService = new CustomUserDetailsService(usuarioRepository);
    }

    @Test
    void loadUserByUsername_conUsuarioExistente_devuelveUserDetails() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setUsername("juan");
        usuario.setPassword("password123");
        usuario.setRole(Role.CLIENTE);

        when(usuarioRepository.findByUsername("juan")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = userDetailsService.loadUserByUsername("juan");

        assertEquals("juan", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE")));
    }

    @Test
    void loadUserByUsername_conUsernameNull_lanzaExcepcion() {
        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(null));

        assertEquals("El nombre de usuario no puede estar vacío.", ex.getMessage());
    }

    @Test
    void loadUserByUsername_conUsernameVacio_lanzaExcepcion() {
        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("   "));

        assertEquals("El nombre de usuario no puede estar vacío.", ex.getMessage());
    }

    @Test
    void loadUserByUsername_usuarioNoEncontrado_lanzaExcepcion() {
        when(usuarioRepository.findByUsername("inexistente")).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("inexistente"));

        assertEquals("Usuario no encontrado con username: inexistente", ex.getMessage());
    }
}
