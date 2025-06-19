package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioRepository {
    Page<UsuarioModel> findAll(Pageable pageable);
    Optional<UsuarioModel> findById(Long id);
    Optional<UsuarioModel> findByEmail(String email);
    Optional<UsuarioModel> findByUsername(String username);
    UsuarioModel save(UsuarioModel model);
    void deleteById(Long id);
    boolean existsById(Long id);
}
