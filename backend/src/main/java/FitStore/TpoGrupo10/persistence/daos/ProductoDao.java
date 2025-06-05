package FitStore.TpoGrupo10.persistence.daos;

import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository<ProductoEntity, Long> {
}
