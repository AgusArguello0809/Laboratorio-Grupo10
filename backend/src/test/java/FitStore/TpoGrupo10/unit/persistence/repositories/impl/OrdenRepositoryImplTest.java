package FitStore.TpoGrupo10.unit.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.OrdenModel;
import FitStore.TpoGrupo10.persistence.daos.OrdenDao;
import FitStore.TpoGrupo10.persistence.entities.OrdenEntity;
import FitStore.TpoGrupo10.persistence.mappers.OrdenPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.impl.OrdenRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdenRepositoryImplTest {

    @Test
    void testSaveAndFindById() {
        OrdenDao dao = mock(OrdenDao.class);
        OrdenPersistenceMapper mapper = mock(OrdenPersistenceMapper.class);
        OrdenRepositoryImpl repo = new OrdenRepositoryImpl(dao, mapper);

        OrdenModel model = new OrdenModel();
        model.setId(10L);

        OrdenEntity entity = new OrdenEntity();
        entity.setId(10L);

        when(mapper.toEntity(model)).thenReturn(entity);
        when(dao.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);
        when(dao.findById(10L)).thenReturn(Optional.of(entity));

        OrdenModel saved = repo.save(model);
        assertNotNull(saved);
        assertEquals(10L, saved.getId());

        Optional<OrdenModel> found = repo.findById(10L);
        assertTrue(found.isPresent());
        assertEquals(10L, found.get().getId());
    }
}
