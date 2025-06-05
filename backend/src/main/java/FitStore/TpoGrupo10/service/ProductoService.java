package FitStore.TpoGrupo10.service;

import FitStore.TpoGrupo10.models.ProductoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<ProductoModel> findAll(Pageable pageable);
    ProductoModel findById(Long id);
    ProductoModel create(ProductoModel producto);
    ProductoModel update(Long id, ProductoModel producto);
    void delete(Long id);
}
