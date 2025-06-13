package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.ProductoModel;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductoRepository {
    Page<ProductoModel> findAll(Predicate predicate, Pageable pageable);
    Optional<ProductoModel> findById(Long id);
    ProductoModel save(ProductoModel model);
    void deleteById(Long id);
    boolean existsById(Long id);
}