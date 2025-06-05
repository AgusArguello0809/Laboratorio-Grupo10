package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioPresentationMapper {


    UsuarioModel toModel(UsuarioDto dto);
    UsuarioDto toDto(UsuarioModel model);
    List<UsuarioDto> toDtoList(List<UsuarioModel> modelList);
}
