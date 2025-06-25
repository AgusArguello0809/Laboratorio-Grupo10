package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.presentation.dto.response.CategoriaDto;
import FitStore.TpoGrupo10.presentation.mappers.CategoriaPresentationMapper;
import FitStore.TpoGrupo10.business.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaPresentationMapper mapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaPresentationMapper mapper) {
        this.categoriaService = categoriaService;
        this.mapper = mapper;
    }

    @Operation(summary = "Obtener categorías paginadas")
    @GetMapping
    public Page<CategoriaDto> findAll(@ParameterObject Pageable pageable) {
            return categoriaService.findAll(pageable).map(mapper::toDto);
    }
}
