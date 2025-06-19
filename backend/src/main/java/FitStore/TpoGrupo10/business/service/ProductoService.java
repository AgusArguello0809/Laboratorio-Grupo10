package FitStore.TpoGrupo10.business.service;

import FitStore.TpoGrupo10.models.ProductoModel;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {
    Page<ProductoModel> findAll(Predicate predicate, Pageable pageable);
    ProductoModel save(ProductoModel model, MultipartFile[] images);
    ProductoModel update(Long id, ProductoModel model, MultipartFile[] images);
    ProductoModel findById(Long id);
    void delete(Long id);

    ProductoModel agregarImagenes(Long id, MultipartFile[] images);

    ProductoModel eliminarImagenes(Long id, List<String> imagesToRemove);

    ProductoModel actualizarPrecioYStock(Long id, double nuevoPrecio, int nuevoStock);
}
