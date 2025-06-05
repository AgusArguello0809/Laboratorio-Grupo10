package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface UsuarioMapper {
    @Mapping(source = "productos", target = "productos")
    UsuarioModel toModel(UsuarioEntity entity);
    @Mapping(source = "productos", target = "productos")
    UsuarioEntity toEntity(UsuarioModel model);
    List<UsuarioModel> toModelList(List<UsuarioEntity> entities);
}
