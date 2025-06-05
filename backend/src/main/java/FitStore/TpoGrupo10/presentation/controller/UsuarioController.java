package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
import FitStore.TpoGrupo10.presentation.mappers.CarritoPresentationMapper;
import FitStore.TpoGrupo10.presentation.mappers.ProductoPresentationMapper;
import FitStore.TpoGrupo10.presentation.mappers.UsuarioPresentationMapper;
import FitStore.TpoGrupo10.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioPresentationMapper mapper;
    private final ProductoPresentationMapper productoPresentationMapper;
    private final CarritoPresentationMapper carritoPresentationMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioPresentationMapper mapper, ProductoPresentationMapper productoPresentationMapper, CarritoPresentationMapper carritoPresentationMapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
        this.productoPresentationMapper = productoPresentationMapper;
        this.carritoPresentationMapper = carritoPresentationMapper;
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

    @GetMapping("/{id}/productos")
    public List<ProductoDto> getProductos(@PathVariable Long id) {
        return usuarioService.getProductosByUsuario(id).stream().map(productoPresentationMapper::toDto).toList();
    }

    @GetMapping("/{id}/carrito")
    public CarritoDto getCarrito(@PathVariable Long id) {
        return carritoPresentationMapper.toDto(usuarioService.getCarritoByUsuarioId(id));
    }
}
