package FitStore.TpoGrupo10.business.service;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UsuarioService {
    Page<UsuarioModel> findAll(Pageable pageable);
    UsuarioModel findById(Long id);
    UsuarioModel findByEmail(String email);
    UsuarioModel save(UsuarioModel model);
    void delete(Long id);
    void cambiarRol(Long id, Role newRole);

    UsuarioModel findByUsername(String username);
}