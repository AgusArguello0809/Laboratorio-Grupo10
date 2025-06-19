package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.config.customizer.ProductoEntityCustomizer;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.presentation.dto.ProductoCreateDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoResponseDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoUpdateDto;
import FitStore.TpoGrupo10.presentation.mappers.ProductoPresentationMapper;
import FitStore.TpoGrupo10.business.service.ProductoService;
import FitStore.TpoGrupo10.models.ProductoModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoPresentationMapper mapper;
    private final ObjectMapper objectMapper;

    public ProductoController(ProductoService productoService, ProductoPresentationMapper mapper, ObjectMapper objectMapper) {
        this.productoService = productoService;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Obtener productos con filtros", description = "Devuelve una lista paginada de productos, permite filtrar por QueryDSL.")
    @GetMapping
    public Page<ProductoResponseDto> getAll(
            @QuerydslPredicate(root = ProductoEntity.class, bindings = ProductoEntityCustomizer.class) Predicate predicate,
            @ParameterObject Pageable pageable) {
            return productoService.findAll(predicate, pageable).map(mapper::toResponseDto);
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve el producto con el ID especificado.")
    @GetMapping("/{id}")
    public ProductoResponseDto getById(@PathVariable Long id) {
            ProductoModel model = productoService.findById(id);
            return mapper.toResponseDto(model);
    }

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto. Requiere enviar los datos y las imágenes.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductoResponseDto save(
            @RequestPart("data") String data,
            @RequestPart("images") MultipartFile[] images) throws JsonProcessingException {
            ProductoCreateDto dto = objectMapper.readValue(data, ProductoCreateDto.class);
            ProductoModel model = mapper.toModel(dto);

            // Tomar el username desde el token (SecurityContext)
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            ProductoModel saved = productoService.save(model, username, images);
            return mapper.toResponseDto(saved);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente. Las imágenes son opcionales.")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductoResponseDto update(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(name = "images", required = false) MultipartFile[] images) throws JsonProcessingException {
            ProductoUpdateDto dto = objectMapper.readValue(data, ProductoUpdateDto.class);
            ProductoModel model = mapper.toModel(dto);
            ProductoModel updated = productoService.update(id, model, images);
            return mapper.toResponseDto(updated);
    }

    @Operation(summary = "Agregar imágenes al producto", description = "Permite agregar nuevas imágenes a un producto existente (máximo 10 en total).")
    @PatchMapping(value = "/{id}/imagenes/agregar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductoResponseDto agregarImagenes(
            @PathVariable Long id,
            @RequestPart("images") MultipartFile[] images) {
            ProductoModel actualizado = productoService.agregarImagenes(id, images);
            return mapper.toResponseDto(actualizado);
    }

    @Operation(summary = "Eliminar imágenes del producto", description = "Permite eliminar imágenes específicas del producto.")
    @PatchMapping("/{id}/imagenes/eliminar")
    public ProductoResponseDto eliminarImagenes(
            @PathVariable Long id,
            @RequestBody List<String> imagesToRemove) {
            ProductoModel actualizado = productoService.eliminarImagenes(id, imagesToRemove);
            return mapper.toResponseDto(actualizado);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto por ID.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
            productoService.delete(id);
    }
}
