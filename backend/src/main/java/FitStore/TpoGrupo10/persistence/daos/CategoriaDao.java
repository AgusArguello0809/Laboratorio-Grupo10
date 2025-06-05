package FitStore.TpoGrupo10.persistence.daos;

import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDao extends JpaRepository<CategoriaEntity, Long> {
}
