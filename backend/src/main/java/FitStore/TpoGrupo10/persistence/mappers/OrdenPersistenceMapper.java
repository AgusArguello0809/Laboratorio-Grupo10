package FitStore.TpoGrupo10.persistence.mappers;
import FitStore.TpoGrupo10.models.OrdenModel;
import FitStore.TpoGrupo10.persistence.entities.OrdenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductoPersistenceMapper.class, ItemOrdenPersistenceMapper.class})
public interface OrdenPersistenceMapper {

    @Mapping(source = "productos", target = "productos")
    OrdenModel toModel(OrdenEntity entity);

    @Mapping(source = "productos", target = "productos")
    OrdenEntity toEntity(OrdenModel model);
}
