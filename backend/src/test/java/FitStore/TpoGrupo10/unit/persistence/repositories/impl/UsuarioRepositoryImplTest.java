package FitStore.TpoGrupo10.unit.persistence.repositories.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.daos.UsuarioDao;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import FitStore.TpoGrupo10.persistence.mappers.UsuarioPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.impl.UsuarioRepositoryImpl;

class UsuarioRepositoryImplTest {

    private UsuarioDao usuarioDao;
    private UsuarioPersistenceMapper mapper;
    private UsuarioRepositoryImpl repository;

    private UsuarioEntity entity;
    private UsuarioModel model;

    @BeforeEach
    void setUp() {
        usuarioDao = mock(UsuarioDao.class);
        mapper = mock(UsuarioPersistenceMapper.class);
        repository = new UsuarioRepositoryImpl(usuarioDao, mapper);

        entity = new UsuarioEntity();
        entity.setId(1L);
        entity.setUsername("lucasc");

        model = new UsuarioModel();
        model.setId(1L);
        model.setUsername("lucasc");
    }

    @Test
    void findAll_devuelveUsuariosPaginados() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<UsuarioEntity> entityPage = new PageImpl<>(java.util.List.of(entity));
        when(usuarioDao.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toModel(entity)).thenReturn(model);

        Page<UsuarioModel> result = repository.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("lucasc", result.getContent().get(0).getUsername());
        verify(usuarioDao).findAll(pageable);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveUsuarioSiExiste() {
        when(usuarioDao.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<UsuarioModel> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("lucasc", result.get().getUsername());
        verify(usuarioDao).findById(1L);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveVacioSiNoExiste() {
        when(usuarioDao.findById(100L)).thenReturn(Optional.empty());

        Optional<UsuarioModel> result = repository.findById(100L);

        assertTrue(result.isEmpty());
        verify(usuarioDao).findById(100L);
        verify(mapper, never()).toModel(any());
    }

    @Test
    void findByEmail_devuelveUsuarioSiExiste() {
        when(usuarioDao.findByEmail("lucas@mail.com")).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<UsuarioModel> result = repository.findByEmail("lucas@mail.com");

        assertTrue(result.isPresent());
        assertEquals("lucasc", result.get().getUsername());
        verify(usuarioDao).findByEmail("lucas@mail.com");
        verify(mapper).toModel(entity);
    }

    @Test
    void findByUsername_devuelveUsuarioSiExiste() {
        when(usuarioDao.findByUsername("lucasc")).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<UsuarioModel> result = repository.findByUsername("lucasc");

        assertTrue(result.isPresent());
        assertEquals("lucasc", result.get().getUsername());
        verify(usuarioDao).findByUsername("lucasc");
        verify(mapper).toModel(entity);
    }

    @Test
    void save_guardaYMapeaUsuario() {
        when(mapper.toEntity(model)).thenReturn(entity);
        when(usuarioDao.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        UsuarioModel result = repository.save(model);

        assertNotNull(result);
        assertEquals("lucasc", result.getUsername());
        verify(mapper).toEntity(model);
        verify(usuarioDao).save(entity);
        verify(mapper).toModel(entity);
    }

    @Test
    void deleteById_eliminaUsuario() {
        repository.deleteById(5L);
        verify(usuarioDao).deleteById(5L);
    }

    @Test
    void existsById_devuelveTrueSiExiste() {
        when(usuarioDao.existsById(1L)).thenReturn(true);

        assertTrue(repository.existsById(1L));
        verify(usuarioDao).existsById(1L);
    }

    @Test
    void existsById_devuelveFalseSiNoExiste() {
        when(usuarioDao.existsById(500L)).thenReturn(false);

        assertFalse(repository.existsById(500L));
        verify(usuarioDao).existsById(500L);
    }
}
