package FitStore.TpoGrupo10.unit.business.service.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.security.crypto.password.PasswordEncoder;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.business.service.impl.UsuarioServiceImpl;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.enums.Role;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;

class UsuarioServiceImplTest {

    private UsuarioRepository repository;
    private PasswordEncoder passwordEncoder;
    private UsuarioServiceImpl service;

    private UsuarioModel usuario;

    @BeforeEach
    void setUp() {
        repository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        service = new UsuarioServiceImpl(repository, passwordEncoder);

        usuario = new UsuarioModel();
        usuario.setId(1L);
        usuario.setUsername("lucas");
        usuario.setPassword("1234");
        usuario.setEmail("lucas@test.com");
    }

    @Test
    void save_usuarioCliente_passwordCodificada() {
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UsuarioModel resultado = service.save(usuario);

        assertEquals("encodedPassword", resultado.getPassword());
        assertEquals(Role.CLIENTE, resultado.getRole());
        verify(repository).save(any());
    }

    @Test
    void findById_usuarioExiste_devuelveUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioModel resultado = service.findById(1L);

        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void findById_usuarioNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.findById(99L));

        assertTrue(ex.getMessage().contains(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage()));
    }

    @Test
    void findByEmail_usuarioExiste_devuelveUsuario() {
        when(repository.findByEmail("lucas@test.com")).thenReturn(Optional.of(usuario));

        UsuarioModel resultado = service.findByEmail("lucas@test.com");

        assertEquals("lucas", resultado.getUsername());
        verify(repository).findByEmail("lucas@test.com");
    }

    @Test
    void delete_usuarioExiste_eliminaUsuario() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_usuarioNoExiste_lanzaExcepcion() {
        when(repository.existsById(99L)).thenReturn(false);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.delete(99L));

        assertTrue(ex.getMessage().contains(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage()));
    }

    @Test
    void cambiarRol_usuarioExiste_actualizaRol() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.cambiarRol(1L, Role.ADMIN);

        assertEquals(Role.ADMIN, usuario.getRole());
        verify(repository).save(usuario);
    }

    @Test
    void cambiarRol_usuarioNoExiste_lanzaExcepcion() {
        when(repository.findById(100L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.cambiarRol(100L, Role.ADMIN));

        assertEquals(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage(), ex.getMessage());
    }
}