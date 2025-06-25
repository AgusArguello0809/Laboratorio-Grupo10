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

import com.querydsl.core.types.Predicate;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.persistence.daos.ProductoDao;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.mappers.ProductoPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.impl.ProductoRepositoryImpl;

class ProductoRepositoryImplTest {

    private ProductoDao productoDao;
    private ProductoPersistenceMapper mapper;
    private ProductoRepositoryImpl repository;

    private ProductoEntity entity;
    private ProductoModel model;

    @BeforeEach
    void setUp() {
        productoDao = mock(ProductoDao.class);
        mapper = mock(ProductoPersistenceMapper.class);
        repository = new ProductoRepositoryImpl(productoDao, mapper);

        entity = new ProductoEntity();
        entity.setId(1L);
        entity.setTitle("Creatina");

        model = new ProductoModel();
        model.setId(1L);
        model.setTitle("Creatina");
    }

    @Test
    void findAll_devuelveProductosPaginados() {
        Predicate predicate = mock(Predicate.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductoEntity> entityPage = new PageImpl<>(java.util.List.of(entity));
        when(productoDao.findAll(predicate, pageable)).thenReturn(entityPage);
        when(mapper.toModel(entity)).thenReturn(model);

        Page<ProductoModel> result = repository.findAll(predicate, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Creatina", result.getContent().get(0).getTitle());
        verify(productoDao).findAll(predicate, pageable);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveProductoSiExiste() {
        when(productoDao.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<ProductoModel> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(productoDao).findById(1L);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveVacioSiNoExiste() {
        when(productoDao.findById(99L)).thenReturn(Optional.empty());

        Optional<ProductoModel> result = repository.findById(99L);

        assertTrue(result.isEmpty());
        verify(productoDao).findById(99L);
        verify(mapper, never()).toModel(any());
    }

    @Test
    void save_guardaYMapeaProducto() {
        when(mapper.toEntity(model)).thenReturn(entity);
        when(productoDao.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        ProductoModel result = repository.save(model);

        assertNotNull(result);
        assertEquals("Creatina", result.getTitle());
        verify(mapper).toEntity(model);
        verify(productoDao).save(entity);
        verify(mapper).toModel(entity);
    }

    @Test
    void deleteById_eliminaProducto() {
        repository.deleteById(5L);
        verify(productoDao).deleteById(5L);
    }

    @Test
    void existsById_devuelveTrueSiExiste() {
        when(productoDao.existsById(1L)).thenReturn(true);

        assertTrue(repository.existsById(1L));
        verify(productoDao).existsById(1L);
    }

    @Test
    void existsById_devuelveFalseSiNoExiste() {
        when(productoDao.existsById(100L)).thenReturn(false);

        assertFalse(repository.existsById(100L));
        verify(productoDao).existsById(100L);
    }
}