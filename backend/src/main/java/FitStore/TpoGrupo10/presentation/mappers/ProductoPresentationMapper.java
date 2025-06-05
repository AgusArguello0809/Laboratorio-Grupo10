package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoPresentationMapper {
    ProductoDto toDto(ProductoModel model);
    ProductoModel toModel(ProductoDto dto);
    List<ProductoDto> toDtoList(List<ProductoModel> modelList);
}