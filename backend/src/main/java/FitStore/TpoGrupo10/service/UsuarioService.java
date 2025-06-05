package FitStore.TpoGrupo10.service;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    Page<UsuarioModel> findAll(Pageable pageable);
    UsuarioModel findById(Long id);
    UsuarioModel findByEmail(String email);
    UsuarioModel save(UsuarioModel model);
    void delete(Long id);
    List<ProductoModel> getProductosByUsuario(Long id);
    CarritoModel getCarritoByUsuarioId(Long id);
}