package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCode;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.business.service.UsuarioService;
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
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + id, ErrorCode.USUARIO_NO_ENCONTRADO));
    }

    @Override
    public UsuarioModel findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con email: " + email, ErrorCode.USUARIO_NO_ENCONTRADO));
    }

    @Override
    public UsuarioModel save(UsuarioModel model) {
        return repository.save(model);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Usuario no encontrado con ID: " + id, ErrorCode.USUARIO_NO_ENCONTRADO);
        }
        repository.deleteById(id);
    }
}

