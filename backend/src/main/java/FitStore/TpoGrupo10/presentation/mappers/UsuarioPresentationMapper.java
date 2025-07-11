package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.create.UsuarioCreateDto;
import FitStore.TpoGrupo10.presentation.dto.response.UsuarioResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioPresentationMapper {

    UsuarioModel toModel(UsuarioCreateDto dto);

    @Mapping(source = "id", target = "id")
    UsuarioResponseDto toResponseDto(UsuarioModel model);

    List<UsuarioResponseDto> toResponseDtoList(List<UsuarioModel> models);
    List<UsuarioModel> toModelList(List<UsuarioCreateDto> dtos);
}