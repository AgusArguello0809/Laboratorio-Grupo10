package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.service.OrdenService;
import org.springframework.stereotype.Service;
import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.OrdenModel;
import FitStore.TpoGrupo10.persistence.repositories.OrdenRepository;

import java.util.List;

@Service
public class OrdenServiceImpl implements OrdenService {
    private final OrdenRepository repository;

    public OrdenServiceImpl(OrdenRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrdenModel save(OrdenModel orden) {
        return repository.save(orden);
    }

    @Override
    public OrdenModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.ORDEN_NO_ENCONTRADA.getMessage(), ErrorCodeEnum.ORDEN_NO_ENCONTRADA));
    }

    @Override
    public List<OrdenModel> findByCompradorId(Long compradorId) {
        return repository.findByCompradorId(compradorId);
    }
}
