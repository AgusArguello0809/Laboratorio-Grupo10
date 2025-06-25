package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.OrdenModel;

import java.util.List;
import java.util.Optional;

public interface OrdenRepository {
    OrdenModel save(OrdenModel model);
    Optional<OrdenModel> findById(Long id);
    List<OrdenModel> findByCompradorId(Long compradorId);
}
