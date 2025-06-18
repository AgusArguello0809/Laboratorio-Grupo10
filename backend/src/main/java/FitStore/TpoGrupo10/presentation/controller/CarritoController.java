package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.presentation.dto.AddProductRequestDto;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.mappers.CarritoPresentationMapper;
import FitStore.TpoGrupo10.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService service;
    private final CarritoPresentationMapper mapper;

    public CarritoController(CarritoService service, CarritoPresentationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Obtener carrito por usuario ID")
    @GetMapping("usuario/{ownerId}")
    public CarritoDto getByUsuarioId(@PathVariable Long ownerId) {
        Optional<CarritoModel> model = service.getCarritoByOwnerId(ownerId);
        return model.map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
    }

    @Operation(summary = "Agregar producto al carrito")
    @PostMapping("usuario/{ownerId}")
    public CarritoDto save(
            @PathVariable Long ownerId,
            @RequestBody AddProductRequestDto requestDto) {
        try {
            CarritoModel model = service.agregarProducto(ownerId, requestDto.getProductId(), requestDto.getCant());
            return mapper.toDto(model);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar producto al carrito", e);
        }
    }

    @Operation(summary = "Eliminar carrito completo")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        try {
            service.deleteCarrito(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar carrito", e);
        }
    }

    @Operation(summary = "Eliminar producto del carrito")
    @DeleteMapping("{id}/producto/{productoId}")
    public void delete(@PathVariable Long id,
                       @PathVariable Long productoId) {
        try {
            service.deleteCarritoProducto(id, productoId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar producto del carrito", e);
        }
    }

    @Operation(summary = "Incrementar cantidad de producto")
    @PatchMapping("/{id}/producto/{productoId}/incrementar")
    public CarritoDto incrementarCantidad(
            @PathVariable Long id,
            @PathVariable Long productoId) {
        try {
            CarritoModel actualizado = service.incrementarCantidad(id, productoId);
            return mapper.toDto(actualizado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al incrementar cantidad del producto", e);
        }
    }

    @Operation(summary = "Disminuir cantidad de producto")
    @PatchMapping("/{id}/producto/{productoId}/disminuir")
    public CarritoDto disminuirCantidad(
            @PathVariable Long id,
            @PathVariable Long productoId) {
        try {
            CarritoModel actualizado = service.disminuirCantidad(id, productoId);
            return mapper.toDto(actualizado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al disminuir cantidad del producto", e);
        }
    }

    @Operation(summary = "Vaciar carrito")
    @DeleteMapping("/{id}/vaciar")
    public void vaciarCarrito(@PathVariable Long id) {
        try {
            service.vaciarCarrito(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al vaciar carrito", e);
        }
    }

    @Operation(summary = "Realizar checkout del carrito")
    @PostMapping("/{id}/checkout")
    public CarritoDto checkout(@PathVariable Long id) throws IOException {
        try {
            CarritoModel model = service.checkout(id);
            return mapper.toDto(model);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al realizar checkout", e);
        }
    }
}
