package FitStore.TpoGrupo10.persistence.mappers;

import org.mapstruct.Mapper;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaModel toModel(CategoriaEntity entity);
    CategoriaEntity toEntity(CategoriaModel model);
    List<CategoriaModel> toModelList(List<CategoriaEntity> entities);
}
