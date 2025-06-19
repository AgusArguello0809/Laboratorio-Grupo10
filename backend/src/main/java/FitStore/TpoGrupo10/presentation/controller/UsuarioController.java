package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.persistence.entities.enums.Role;
import FitStore.TpoGrupo10.presentation.dto.UpdateRoleDto;
import FitStore.TpoGrupo10.presentation.dto.UsuarioResponseDto;
import FitStore.TpoGrupo10.presentation.mappers.UsuarioPresentationMapper;
import FitStore.TpoGrupo10.business.service.UsuarioService;
import FitStore.TpoGrupo10.models.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
            return usuarioService.findAll(pageable).map(mapper::toResponseDto);
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("/{id}")
    public UsuarioResponseDto getById(@PathVariable Long id) {
            UsuarioModel model = usuarioService.findById(id);
            return mapper.toResponseDto(model);
    }

    @Operation(summary = "Buscar usuario por email")
    @GetMapping("/by-email")
    public UsuarioResponseDto getByEmail(@RequestParam String email) {
            UsuarioModel model = usuarioService.findByEmail(email);
            return mapper.toResponseDto(model);
    }

    @Operation(summary = "Eliminar usuario")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
            usuarioService.delete(id);
    }

    @Operation(summary = "Cambiar el rol de un usuario")
    @PatchMapping("/{id}/rol")
    public void cambiarRol(@PathVariable Long id, @RequestBody UpdateRoleDto dto) {
        Role nuevoRol = Role.valueOf(dto.getNewRole().toUpperCase());
        usuarioService.cambiarRol(id, nuevoRol);
    }
}
