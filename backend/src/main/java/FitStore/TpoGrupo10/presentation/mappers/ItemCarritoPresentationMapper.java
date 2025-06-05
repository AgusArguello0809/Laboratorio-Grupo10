package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.presentation.dto.ItemCarritoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemCarritoPresentationMapper {
    ItemCarritoDto toDto(ItemCarritoModel model);
    ItemCarritoModel toModel(ItemCarritoDto dto);
    List<ItemCarritoDto> toDtoList(List<ItemCarritoModel> models);
    List<ItemCarritoModel> toModelList(List<ItemCarritoDto> dtos);
}
