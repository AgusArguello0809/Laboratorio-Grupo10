package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.daos.UsuarioDao;
import FitStore.TpoGrupo10.persistence.mappers.UsuarioMapper;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);
    private final UsuarioDao dao;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryImpl(UsuarioDao dao, UsuarioMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        try {
            LOGGER.debug("Buscando todos los usuarios de la página {} con tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
            return dao.findAll(pageable).map(mapper::toModel);
        } catch (DataAccessException e) {
            LOGGER.error("Error buscando usuarios", e);
            throw new RuntimeException("Error buscando usuarios", e);
        }
    }

    @Override
    public Optional<UsuarioModel> findById(Long id) {
        try {
            return dao.findById(id).map(mapper::toModel);
        } catch (DataAccessException e) {
            LOGGER.error("Error buscando usuario con id {}", id, e);
            throw new RuntimeException("Error buscando usuario", e);
        }
    }

    @Override
    public Optional<UsuarioModel> findByEmail(String email) {
        try {
            return dao.findByEmail(email).map(mapper::toModel);
        } catch (DataAccessException e) {
            LOGGER.error("Error buscando usuario con email {}", email, e);
            throw new RuntimeException("Error buscando usuario", e);
        }
    }

    @Override
    @Transactional
    public UsuarioModel save(UsuarioModel model) {
        try {
            return mapper.toModel(dao.save(mapper.toEntity(model)));
        } catch (DataAccessException e) {
            LOGGER.error("Error guardando usuario", e);
            throw new RuntimeException("Error guardando usuario", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            dao.deleteById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Error eliminando usuario con id {}", id, e);
            throw new RuntimeException("Error eliminando usuario", e);
        }
    }
}
