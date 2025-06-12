package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoPresentationMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "owner.id", target = "ownerId")
    ProductoDto toDto(ProductoModel model);
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "ownerId", target = "owner.id")
    ProductoModel toModel(ProductoDto dto);
    List<ProductoDto> toDtoList(List<ProductoModel> modelList);
}