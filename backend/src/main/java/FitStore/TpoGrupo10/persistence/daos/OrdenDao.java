package FitStore.TpoGrupo10.persistence.daos;

import FitStore.TpoGrupo10.persistence.entities.OrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenDao extends JpaRepository<OrdenEntity, Long> {
    List<OrdenEntity> findByCompradorId(Long compradorId);
}
