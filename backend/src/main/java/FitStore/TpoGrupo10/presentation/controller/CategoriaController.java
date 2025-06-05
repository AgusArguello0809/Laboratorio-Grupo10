package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.presentation.dto.CategoriaDto;
import FitStore.TpoGrupo10.presentation.mappers.CategoriaPresentationMapper;
import FitStore.TpoGrupo10.service.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaPresentationMapper mapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaPresentationMapper mapper) {
        this.categoriaService = categoriaService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<CategoriaDto> findAll(Pageable pageable) {
        return categoriaService.findAll(pageable).map(mapper::toDto);
    }
}
