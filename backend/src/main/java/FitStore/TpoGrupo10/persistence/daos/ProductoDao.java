package FitStore.TpoGrupo10.persistence.daos;

import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductoDao extends JpaRepository<ProductoEntity, Long>, QuerydslPredicateExecutor<ProductoEntity> {
}
