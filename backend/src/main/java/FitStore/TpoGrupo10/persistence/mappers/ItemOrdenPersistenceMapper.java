package FitStore.TpoGrupo10.persistence.mappers;
import FitStore.TpoGrupo10.models.ItemOrdenModel;
import FitStore.TpoGrupo10.persistence.entities.ItemOrdenEmbeddable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemOrdenPersistenceMapper {
    ItemOrdenModel toModel(ItemOrdenEmbeddable embeddable);
    ItemOrdenEmbeddable toEntity(ItemOrdenModel model);

    List<ItemOrdenModel> toModelList(List<ItemOrdenEmbeddable> embeddables);
    List<ItemOrdenEmbeddable> toEntityList(List<ItemOrdenModel> models);
}
