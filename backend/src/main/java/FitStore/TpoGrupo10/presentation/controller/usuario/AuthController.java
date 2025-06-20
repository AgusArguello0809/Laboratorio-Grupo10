package FitStore.TpoGrupo10.presentation.controller.usuario;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.AuthRequestDto;
import FitStore.TpoGrupo10.presentation.dto.response.AuthResponseDto;
import FitStore.TpoGrupo10.presentation.dto.UsuarioCreateDto;
import FitStore.TpoGrupo10.presentation.dto.response.UsuarioResponseDto;
import FitStore.TpoGrupo10.presentation.mappers.UsuarioPresentationMapper;
import FitStore.TpoGrupo10.security.CustomUserDetailsService;
import FitStore.TpoGrupo10.business.service.UsuarioService;
import FitStore.TpoGrupo10.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final UsuarioPresentationMapper mapper;

    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            UsuarioService usuarioService,
            UsuarioPresentationMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @Operation(summary = "Iniciar sesión con un usuario")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponseDto(jwt));
    }

    @Operation(summary = "Registrar usuario")
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDto> register(@RequestBody @Valid UsuarioCreateDto dto) {
        UsuarioModel saved = usuarioService.save(mapper.toModel(dto));
        UsuarioResponseDto responseDto = mapper.toResponseDto(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
