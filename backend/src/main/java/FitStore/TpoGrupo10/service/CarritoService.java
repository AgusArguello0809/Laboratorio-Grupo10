package FitStore.TpoGrupo10.service;

import FitStore.TpoGrupo10.models.CarritoModel;

import java.util.Optional;

public interface CarritoService {
    Optional<CarritoModel> getCarritoByOwnerId(Long ownerId);
    CarritoModel save(CarritoModel model);
    void deleteCarrito(Long id);
}
