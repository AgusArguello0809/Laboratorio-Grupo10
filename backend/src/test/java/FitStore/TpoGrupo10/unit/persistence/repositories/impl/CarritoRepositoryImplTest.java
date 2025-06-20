package FitStore.TpoGrupo10.unit.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.persistence.daos.CarritoDao;
import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import FitStore.TpoGrupo10.persistence.mappers.CarritoPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.impl.CarritoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CarritoRepositoryImplTest {

    private CarritoDao carritoDao;
    private CarritoPersistenceMapper mapper;
    private CarritoRepositoryImpl repository;

    private CarritoEntity entity;
    private CarritoModel model;

    @BeforeEach
    void setUp() {
        carritoDao = mock(CarritoDao.class);
        mapper = mock(CarritoPersistenceMapper.class);
        repository = new CarritoRepositoryImpl(carritoDao, mapper);

        entity = new CarritoEntity();
        entity.setId(1L);
        model = new CarritoModel();
        model.setId(1L);
    }

    @Test
    void findById_devuelveCarritoSiExiste() {
        when(carritoDao.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<CarritoModel> resultado = repository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(carritoDao).findById(1L);
        verify(mapper).toModel(entity);
    }

    @Test
    void findById_devuelveVacioSiNoExiste() {
        when(carritoDao.findById(99L)).thenReturn(Optional.empty());

        Optional<CarritoModel> resultado = repository.findById(99L);

        assertTrue(resultado.isEmpty());
        verify(carritoDao).findById(99L);
        verify(mapper, never()).toModel(any());
    }

    @Test
    void findByOwnerId_devuelveCarritoSiExiste() {
        when(carritoDao.findByOwnerId(2L)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Optional<CarritoModel> resultado = repository.findByOwnerId(2L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(carritoDao).findByOwnerId(2L);
        verify(mapper).toModel(entity);
    }

    @Test
    void findByOwnerId_devuelveVacioSiNoExiste() {
        when(carritoDao.findByOwnerId(77L)).thenReturn(Optional.empty());

        Optional<CarritoModel> resultado = repository.findByOwnerId(77L);

        assertTrue(resultado.isEmpty());
        verify(carritoDao).findByOwnerId(77L);
        verify(mapper, never()).toModel(any());
    }

    @Test
    void save_guardaYMapeaCarrito() {
        when(mapper.toEntity(model)).thenReturn(entity);
        when(carritoDao.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        CarritoModel resultado = repository.save(model);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(mapper).toEntity(model);
        verify(carritoDao).save(entity);
        verify(mapper).toModel(entity);
    }

    @Test
    void deleteById_eliminaCarrito() {
        repository.deleteById(10L);
        verify(carritoDao).deleteById(10L);
    }
}
