package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoModel toModel(ProductoEntity entity);
    ProductoEntity toEntity(ProductoModel model);
    List<ProductoModel> toModelList(List<ProductoEntity> entities);
}
