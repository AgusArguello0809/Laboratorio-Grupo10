package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.presentation.dto.CategoriaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaPresentationMapper {

    CategoriaModel toModel(CategoriaDto dto);
    CategoriaDto toDto(CategoriaModel model);
    List<CategoriaDto> toDtoList(List<CategoriaModel> models);
    List<CategoriaModel> toModelList(List<CategoriaDto> dtos);
}
