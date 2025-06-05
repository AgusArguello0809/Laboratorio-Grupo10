package FitStore.TpoGrupo10.presentation.controller;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.mappers.CarritoPresentationMapper;
import FitStore.TpoGrupo10.service.CarritoService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/usuario/{ownerId}")
    public CarritoDto getByUsuarioId(@PathVariable Long ownerId) {
        Optional<CarritoModel> model = service.getCarritoByOwnerId(ownerId);
        return model.map(mapper::toDto).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    @PostMapping
    public CarritoDto save(@RequestBody CarritoDto dto) {
        CarritoModel model = service.save(mapper.toModel(dto));
        return mapper.toDto(model);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCarrito(id);
    }
}
