package FitStore.TpoGrupo10.presentation.controller;

import org.springframework.web.bind.annotation.*;
import FitStore.TpoGrupo10.service.ProductoService;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import FitStore.TpoGrupo10.presentation.mappers.ProductoPresentationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoPresentationMapper mapper;

    public ProductoController(ProductoService productoService, ProductoPresentationMapper mapper) {
        this.productoService = productoService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<ProductoDto> getAll(Pageable pageable) {
        return productoService.findAll(pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public ProductoDto getById(@PathVariable Long id) {
        return mapper.toDto(productoService.findById(id));
    }

    @PostMapping
    public ProductoDto create(@RequestBody ProductoDto dto) {
        return mapper.toDto(productoService.create(mapper.toModel(dto)));
    }

    @PutMapping("/{id}")
    public ProductoDto update(@PathVariable Long id, @RequestBody ProductoDto dto) {
        return mapper.toDto(productoService.update(id, mapper.toModel(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productoService.delete(id);
    }
}
