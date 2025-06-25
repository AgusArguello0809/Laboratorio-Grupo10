package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.persistence.repositories.OrdenRepository;
import org.springframework.stereotype.Repository;
import FitStore.TpoGrupo10.models.OrdenModel;
import FitStore.TpoGrupo10.persistence.daos.OrdenDao;
import FitStore.TpoGrupo10.persistence.mappers.OrdenPersistenceMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdenRepositoryImpl implements OrdenRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenRepositoryImpl.class);
    private final OrdenDao dao;
    private final OrdenPersistenceMapper mapper;

    public OrdenRepositoryImpl(OrdenDao dao, OrdenPersistenceMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public OrdenModel save(OrdenModel model) {
        LOGGER.debug("Guardando orden: {}", model);
        return mapper.toModel(dao.save(mapper.toEntity(model)));
    }

    @Override
    public Optional<OrdenModel> findById(Long id) {
        LOGGER.debug("Buscando orden con ID {}", id);
        return dao.findById(id).map(mapper::toModel);
    }

    @Override
    public List<OrdenModel> findByCompradorId(Long compradorId) {
        LOGGER.debug("Buscando órdenes del comprador con ID {}", compradorId);
        return dao.findByCompradorId(compradorId).stream()
                .map(mapper::toModel)
                .toList();
    }
}
