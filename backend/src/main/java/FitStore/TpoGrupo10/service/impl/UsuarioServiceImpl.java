package FitStore.TpoGrupo10.service.impl;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public UsuarioModel findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public UsuarioModel findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public UsuarioModel save(UsuarioModel model) {
        return repository.save(model);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }


}
