package FitStore.TpoGrupo10.unit.persistence.repositories.impl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.daos.CategoriaDao;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import FitStore.TpoGrupo10.persistence.mappers.CategoriaPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.impl.CategoriaRepositoryImpl;

class CategoriaRepositoryImplTest {

    private CategoriaDao categoriaDao;
    private CategoriaPersistenceMapper mapper;
    private CategoriaRepositoryImpl repository;

    private CategoriaEntity entity;
    private CategoriaModel model;

    @BeforeEach
    void setUp() {
        categoriaDao = mock(CategoriaDao.class);
        mapper = mock(CategoriaPersistenceMapper.class);
        repository = new CategoriaRepositoryImpl(categoriaDao, mapper);

        entity = new CategoriaEntity();
        entity.setId(1L);
        entity.setNombre("Suplementos");

        model = new CategoriaModel();
        model.setId(1L);
        model.setNombre("Suplementos");
    }

    @Test
    void findAll_devuelveCategoriasPaginadas() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<CategoriaEntity> entityPage = new PageImpl<>(List.of(entity));
        when(categoriaDao.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toModel(entity)).thenReturn(model);

        Page<CategoriaModel> resultado = repository.findAll(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Suplementos", resultado.getContent().get(0).getNombre());
        verify(categoriaDao).findAll(pageable);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveCategoriaSiExiste() {
        when(categoriaDao.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<CategoriaModel> resultado = repository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Suplementos", resultado.get().getNombre());
        verify(categoriaDao).findById(1L);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveVacioSiNoExiste() {
        when(categoriaDao.findById(99L)).thenReturn(Optional.empty());

        Optional<CategoriaModel> resultado = repository.findById(99L);

        assertTrue(resultado.isEmpty());
        verify(categoriaDao).findById(99L);
        verify(mapper, never()).toModel(any());
    }
}
