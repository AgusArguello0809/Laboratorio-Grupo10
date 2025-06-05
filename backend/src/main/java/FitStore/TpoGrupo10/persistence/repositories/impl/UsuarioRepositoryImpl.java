package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.daos.UsuarioDao;
import FitStore.TpoGrupo10.persistence.mappers.UsuarioMapper;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioDao dao;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryImpl(UsuarioDao dao, UsuarioMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        return dao.findAll(pageable).map(mapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findById(Long id) {
        return dao.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findByEmail(String email) {
        return dao.findByEmail(email).map(mapper::toModel);
    }

    @Override
    public UsuarioModel save(UsuarioModel model) {
        return mapper.toModel(dao.save(mapper.toEntity(model)));
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

}
