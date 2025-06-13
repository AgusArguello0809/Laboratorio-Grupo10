package FitStore.TpoGrupo10.service;

import FitStore.TpoGrupo10.models.ProductoModel;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductoService {
    Page<ProductoModel> findAll(Predicate predicate, Pageable pageable);
    ProductoModel save(ProductoModel model, MultipartFile[] images) throws IOException;
    ProductoModel update(Long id, ProductoModel model, MultipartFile[] images) throws IOException;
    ProductoModel findById(Long id);
    void delete(Long id);

    ProductoModel agregarImagenes(Long id, MultipartFile[] images) throws IOException;

    ProductoModel eliminarImagenes(Long id, List<String> imagesToRemove);

    ProductoModel actualizarPrecioYStock(Long id, double nuevoPrecio, int nuevoStock);
}
