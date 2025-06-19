package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.presentation.dto.AddProductRequestDto;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.mappers.CarritoPresentationMapper;
import FitStore.TpoGrupo10.business.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService service;
    private final CarritoPresentationMapper mapper;

    public CarritoController(CarritoService service, CarritoPresentationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Obtener carrito del usuario autenticado")
    @GetMapping
    public CarritoDto getByUsuario() {
        CarritoModel model = service.getCarritoByOwnerId();
        return mapper.toDto(model);
    }

    @Operation(summary = "Agregar producto al carrito del usuario autenticado")
    @PostMapping
    public CarritoDto save(@RequestBody AddProductRequestDto requestDto) {
        CarritoModel model = service.agregarProducto(requestDto.getProductId(), requestDto.getCant());
        return mapper.toDto(model);
    }

    @Operation(summary = "Eliminar producto del carrito")
    @DeleteMapping("{id}/producto/{productoId}")
    public void deleteProductoCarrito(@PathVariable Long id,
                                      @PathVariable Long productoId) {
            service.deleteProductoCarrito(id, productoId);
    }

    @Operation(summary = "Incrementar cantidad de producto")
    @PatchMapping("/{id}/producto/{productoId}/incrementar")
    public CarritoDto incrementarCantidad(
            @PathVariable Long id,
            @PathVariable Long productoId) {
            CarritoModel actualizado = service.incrementarCantidad(id, productoId);
            return mapper.toDto(actualizado);
    }

    @Operation(summary = "Disminuir cantidad de producto")
    @PatchMapping("/{id}/producto/{productoId}/disminuir")
    public CarritoDto disminuirCantidad(
            @PathVariable Long id,
            @PathVariable Long productoId) {
            CarritoModel actualizado = service.disminuirCantidad(id, productoId);
            return mapper.toDto(actualizado);
    }

    @Operation(summary = "Vaciar carrito")
    @DeleteMapping("/{id}/vaciar")
    public void vaciarCarrito(@PathVariable Long id) {
            service.vaciarCarrito(id);
    }

    @Operation(summary = "Realizar checkout del carrito")
    @PostMapping("/{id}/checkout")
    public CarritoDto checkout(@PathVariable Long id) {
            CarritoModel model = service.checkout(id);
            return mapper.toDto(model);
    }

    @Operation(summary = "Eliminar cualquier carrito", description = "ADVERTENCIA: SOLO EL ADMIN PUEDE USARLO. EL USO INDEBIDO POR EL PERSONAL ESTA INAUTORIZADO")
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCarritoComoAdmin(@PathVariable Long id) {
        service.deleteCarrito(id);
    }
}
