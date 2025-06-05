package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.CarritoModel;

import java.util.Optional;

public interface CarritoRepository {
    Optional<CarritoModel> findById(Long id);
    Optional<CarritoModel> findByOwnerId(Long ownerId);
    CarritoModel save(CarritoModel model);
    void deleteById(Long id);
}
