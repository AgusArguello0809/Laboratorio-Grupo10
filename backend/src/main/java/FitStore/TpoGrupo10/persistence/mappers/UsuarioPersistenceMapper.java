package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioPersistenceMapper {
    UsuarioModel toModel(UsuarioEntity entity);
    UsuarioEntity toEntity(UsuarioModel model);
    List<UsuarioModel> toModelList(List<UsuarioEntity> entities);
}
