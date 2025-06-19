package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemCarritoPersistenceMapper {
    ItemCarritoModel toModel(ItemCarritoEmbeddable embeddable);
    ItemCarritoEmbeddable toEntity(ItemCarritoModel model);
    List<ItemCarritoModel> toModelList(List<ItemCarritoEmbeddable> embeddables);
    List<ItemCarritoEmbeddable> toEntityList(List<ItemCarritoModel> models);
}
