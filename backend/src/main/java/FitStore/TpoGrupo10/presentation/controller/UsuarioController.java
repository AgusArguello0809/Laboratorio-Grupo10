package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
import FitStore.TpoGrupo10.presentation.mappers.UsuarioPresentationMapper;
import FitStore.TpoGrupo10.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioPresentationMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioPresentationMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<UsuarioDto> getAll(Pageable pageable) {
        return usuarioService.findAll(pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public UsuarioDto getById(@PathVariable Long id) {
        return mapper.toDto(usuarioService.findById(id));
    }

    @GetMapping("/email/{email}")
    public UsuarioDto getByEmail(@PathVariable String email) {
        return mapper.toDto(usuarioService.findByEmail(email));
    }

    @PostMapping
    public UsuarioDto create(@RequestBody UsuarioDto dto) {
        return mapper.toDto(usuarioService.save(mapper.toModel(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }
}
