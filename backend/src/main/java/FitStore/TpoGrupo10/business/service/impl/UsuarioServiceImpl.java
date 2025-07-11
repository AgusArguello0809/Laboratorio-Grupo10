package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.enums.Role;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.business.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static FitStore.TpoGrupo10.persistence.entities.enums.Role.CLIENTE;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public UsuarioModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage() + ", id: " + id, ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
    }

    @Override
    public UsuarioModel findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage() + ", email: " + email, ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
    }

    @Override
    public UsuarioModel save(UsuarioModel model) {
        model.setRole(CLIENTE);
        model.setPassword(passwordEncoder.encode(model.getPassword()));
        return repository.save(model);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage() + ", id: " + id, ErrorCodeEnum.USUARIO_NO_ENCONTRADO);
        }
        repository.deleteById(id);
    }

    @Override
    public void cambiarRol(Long id, Role newRole) {
        UsuarioModel usuario = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
        usuario.setRole(newRole);
        repository.save(usuario);
    }

    @Override
    public UsuarioModel findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
    }

}