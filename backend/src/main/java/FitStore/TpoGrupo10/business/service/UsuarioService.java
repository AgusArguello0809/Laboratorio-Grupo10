package FitStore.TpoGrupo10.business.service;

import FitStore.TpoGrupo10.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UsuarioService {
    Page<UsuarioModel> findAll(Pageable pageable);
    UsuarioModel findById(Long id);
    UsuarioModel findByEmail(String email);
    UsuarioModel save(UsuarioModel model);
    void delete(Long id);
}