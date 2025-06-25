package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.ItemOrdenModel;
import FitStore.TpoGrupo10.presentation.dto.response.ItemOrdenDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemOrdenPresentationMapper {

    ItemOrdenDto toDto(ItemOrdenModel model);

    ItemOrdenModel toModel(ItemOrdenDto dto);

    List<ItemOrdenDto> toDtoList(List<ItemOrdenModel> models);

    List<ItemOrdenModel> toModelList(List<ItemOrdenDto> dtos);
}
