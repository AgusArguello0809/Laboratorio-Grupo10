package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.presentation.dto.response.CarritoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemCarritoPresentationMapper.class)
public interface CarritoPresentationMapper {
    @Mapping(source = "productos", target = "productos")
    CarritoDto toDto(CarritoModel model);
    @Mapping(source = "productos", target = "productos")
    CarritoModel toModel(CarritoDto dto);
}
