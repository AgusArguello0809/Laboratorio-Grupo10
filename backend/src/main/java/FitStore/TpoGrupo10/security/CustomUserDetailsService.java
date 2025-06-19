package FitStore.TpoGrupo10.security;

import java.util.List;

import FitStore.TpoGrupo10.models.UsuarioModel;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("El nombre de usuario no puede estar vacío.");
        }

        UsuarioModel usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        System.out.println("Buscando usuario con username: " + username);
        System.out.println("Usuario encontrado: " + usuario.getUsername() + ", password (hash) = " + usuario.getPassword());

        return new User(usuario.getUsername(), usuario.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())));
    }
}
