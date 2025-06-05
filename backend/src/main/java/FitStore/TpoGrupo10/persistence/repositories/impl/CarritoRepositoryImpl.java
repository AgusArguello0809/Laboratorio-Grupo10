package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.persistence.daos.CarritoDao;
import FitStore.TpoGrupo10.persistence.mappers.CarritoMapper;
import FitStore.TpoGrupo10.persistence.repositories.CarritoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CarritoRepositoryImpl implements CarritoRepository {

    private final CarritoDao dao;
    private final CarritoMapper mapper;

    public CarritoRepositoryImpl(CarritoDao dao, CarritoMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Optional<CarritoModel> findById(Long id) {
        return dao.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<CarritoModel> findByOwnerId(Long ownerId) {
        return dao.findByOwnerId(ownerId).map(mapper::toModel);
    }

    @Override
    public CarritoModel save(CarritoModel model) {
        return mapper.toModel(dao.save(mapper.toEntity(model)));
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
