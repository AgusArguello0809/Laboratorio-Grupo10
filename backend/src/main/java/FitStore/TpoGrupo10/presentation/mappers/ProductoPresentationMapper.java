package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.presentation.dto.ProductoCreateDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoUpdateDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductoPresentationMapper {

    @Mapping(source = "categoryId", target = "category.id")
    ProductoModel toModel(ProductoCreateDto dto);

    @Mapping(source = "categoryId", target = "category.id")
    ProductoModel toModel(ProductoUpdateDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "images", target = "images")
    ProductoResponseDto toResponseDto(ProductoModel model);

    List<ProductoResponseDto> toResponseDtoList(List<ProductoModel> modelList);
}