package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.ProductoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoRepository {
    Page<ProductoModel> findAll(Pageable pageable);
    ProductoModel findById(Long id);
    ProductoModel save(ProductoModel model);
    void deleteById(Long id);
    boolean existsById(Long id);
}

