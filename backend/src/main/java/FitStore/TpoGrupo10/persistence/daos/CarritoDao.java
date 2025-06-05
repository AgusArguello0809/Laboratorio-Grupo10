package FitStore.TpoGrupo10.persistence.daos;

import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoDao extends JpaRepository<CarritoEntity, Long> {
    Optional<CarritoEntity> findByOwnerId(Long ownerId);
}
