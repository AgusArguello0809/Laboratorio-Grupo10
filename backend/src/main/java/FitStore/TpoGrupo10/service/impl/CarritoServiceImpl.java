package FitStore.TpoGrupo10.service.impl;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.persistence.repositories.CarritoRepository;
import FitStore.TpoGrupo10.service.CarritoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;

    public CarritoServiceImpl(CarritoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CarritoModel> getCarritoByOwnerId(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Override
    public CarritoModel save(CarritoModel model) {
        return repository.save(model);
    }

    @Override
    public void deleteCarrito(Long id) {
        repository.deleteById(id);
    }
}
