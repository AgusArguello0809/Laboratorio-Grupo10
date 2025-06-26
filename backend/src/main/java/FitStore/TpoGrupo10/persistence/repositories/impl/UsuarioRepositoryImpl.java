package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.daos.UsuarioDao;
import FitStore.TpoGrupo10.persistence.mappers.UsuarioPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);
    private final UsuarioDao dao;
    private final UsuarioPersistenceMapper mapper;

    public UsuarioRepositoryImpl(UsuarioDao dao, UsuarioPersistenceMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        LOGGER.debug("Buscando todos los usuarios de la pagina {} con size {}", pageable.getPageNumber(), pageable.getPageSize());
        return dao.findAll(pageable).map(mapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findById(Long id) {
        LOGGER.debug("Buscando usuario con id:  {}", id);
        return dao.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findByEmail(String email) {
        LOGGER.debug("Buscando usuario con email: {}", email);
        return dao.findByEmail(email).map(mapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findByUsername(String username) {
        LOGGER.debug("Buscando usuario con nombre de usuario: {}", username);
        return dao.findByUsername(username).map(mapper::toModel);
    }

    @Override
    @Transactional
    public UsuarioModel save(UsuarioModel model) {
        LOGGER.debug("Guardando usuario: {}", model);
        return mapper.toModel(dao.save(mapper.toEntity(model)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        LOGGER.debug("Eliminando usuario con id: {}", id);
        dao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LOGGER.info("Comprobando existencia de usuario con id: {}", id);
        return dao.existsById(id);
    }
}
