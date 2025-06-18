package FitStore.TpoGrupo10.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .map(usuarioModel -> {
                    // Map UsuarioModel to UsuarioEntity if possible, or adjust as needed
                    UsuarioEntity entity = new UsuarioEntity();
                    entity.setId(usuarioModel.getId());
                    entity.setEmail(usuarioModel.getEmail());
                    entity.setPassword(usuarioModel.getPassword());
                    // Set other fields as necessary
                    return entity;
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(usuario.getEmail(), usuario.getPassword(), Collections.emptyList());
    }
}
