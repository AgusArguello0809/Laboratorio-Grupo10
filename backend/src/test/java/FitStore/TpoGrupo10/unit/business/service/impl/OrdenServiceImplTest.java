package FitStore.TpoGrupo10.unit.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.business.service.impl.OrdenServiceImpl;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.OrdenModel;
import FitStore.TpoGrupo10.persistence.repositories.OrdenRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdenServiceImplTest {

    @Test
    void testSaveAndFindById() {
        OrdenRepository repository = mock(OrdenRepository.class);
        OrdenModel model = new OrdenModel();
        model.setId(5L);

        when(repository.save(model)).thenReturn(model);
        when(repository.findById(5L)).thenReturn(Optional.of(model));

        OrdenServiceImpl service = new OrdenServiceImpl(repository);

        assertEquals(model, service.save(model));
        assertEquals(model, service.findById(5L));
    }

    @Test
    void testFindByIdThrowsIfMissing() {
        OrdenRepository repository = mock(OrdenRepository.class);
        when(repository.findById(123L)).thenReturn(Optional.empty());

        OrdenServiceImpl service = new OrdenServiceImpl(repository);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.findById(123L));
        assertEquals(ErrorCodeEnum.ORDEN_NO_ENCONTRADA, ex.getCode());
    }
}
