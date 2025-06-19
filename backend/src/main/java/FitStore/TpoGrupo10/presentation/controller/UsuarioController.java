package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.presentation.dto.UsuarioResponseDto;
import FitStore.TpoGrupo10.presentation.mappers.UsuarioPresentationMapper;
import FitStore.TpoGrupo10.service.UsuarioService;
import FitStore.TpoGrupo10.models.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioPresentationMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioPresentationMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @Operation(summary = "Obtener usuarios paginados")
    @GetMapping
    public Page<UsuarioResponseDto> getAll(@ParameterObject Pageable pageable) {
        try {
            return usuarioService.findAll(pageable).map(mapper::toResponseDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener usuarios", e);
        }
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("/{id}")
    public UsuarioResponseDto getById(@PathVariable Long id) {
        try {
            UsuarioModel model = usuarioService.findById(id);
            return mapper.toResponseDto(model);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + id, e);
        }
    }

    @Operation(summary = "Buscar usuario por email")
    @GetMapping("/by-email")
    public UsuarioResponseDto getByEmail(@RequestParam String email) {
        try {
            UsuarioModel model = usuarioService.findByEmail(email);
            return mapper.toResponseDto(model);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con email: " + email, e);
        }
    }

    @Operation(summary = "Eliminar usuario")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar usuario", e);
        }
    }
}
