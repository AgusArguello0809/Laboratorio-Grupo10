package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductoPresentationMapper.class)
public interface UsuarioPresentationMapper {


    @Mapping(source = "productos", target = "productos")
    UsuarioModel toModel(UsuarioDto dto);
    @Mapping(source = "productos", target = "productos")
    UsuarioDto toDto(UsuarioModel model);
    List<UsuarioDto> toDtoList(List<UsuarioModel> modelList);
}
