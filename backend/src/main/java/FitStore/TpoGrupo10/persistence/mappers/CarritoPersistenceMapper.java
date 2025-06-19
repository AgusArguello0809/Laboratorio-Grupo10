package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductoPersistenceMapper.class, ItemCarritoPersistenceMapper.class})
public interface CarritoPersistenceMapper {
    @Mapping(source = "productos", target = "productos")
    CarritoModel toModel(CarritoEntity entity);
    @Mapping(source = "productos", target = "productos")
    CarritoEntity toEntity(CarritoModel model);
}
