package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

public interface UsuarioRepository {
    Page<UsuarioModel> findAll(Pageable pageable);
    Optional<UsuarioModel> findById(Long id);
    Optional<UsuarioModel> findByEmail(String email);
    UsuarioModel save(UsuarioModel model);
    void deleteById(Long id);
    List<ProductoModel> findAllProductosByOwnerId(Long id);
    CarritoModel findCarritoByOwnerId(Long id);
}
