package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductoPresentationMapper.class)
public interface CarritoPresentationMapper {
    @Mapping(source = "productos", target = "productos")
    @Mapping(source = "owner.id", target = "ownerId")
    CarritoDto toDto(CarritoModel model);
    @Mapping(source = "productos", target = "productos")
    @Mapping(source = "ownerId", target = "owner.id")
    CarritoModel toModel(CarritoDto dto);
}
