package FitStore.TpoGrupo10.persistence.mappers;

import org.mapstruct.Mapper;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface CategoriaMapper {

    @Mapping(source = "productos", target = "productos")
    CategoriaModel toModel(CategoriaEntity entity);
    @Mapping(source = "productos", target = "productos")
    CategoriaEntity toEntity(CategoriaModel model);
    List<CategoriaModel> toModelList(List<CategoriaEntity> entities);
}
