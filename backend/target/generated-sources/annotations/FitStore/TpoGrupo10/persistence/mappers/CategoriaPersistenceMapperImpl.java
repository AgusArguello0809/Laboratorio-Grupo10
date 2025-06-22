package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-20T15:51:27-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class CategoriaPersistenceMapperImpl implements CategoriaPersistenceMapper {

    @Override
    public CategoriaModel toModel(CategoriaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoriaModel categoriaModel = new CategoriaModel();

        categoriaModel.setId( entity.getId() );
        categoriaModel.setNombre( entity.getNombre() );

        return categoriaModel;
    }

    @Override
    public CategoriaEntity toEntity(CategoriaModel model) {
        if ( model == null ) {
            return null;
        }

        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setId( model.getId() );
        categoriaEntity.setNombre( model.getNombre() );

        return categoriaEntity;
    }

    @Override
    public List<CategoriaModel> toModelList(List<CategoriaEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoriaModel> list = new ArrayList<CategoriaModel>( entities.size() );
        for ( CategoriaEntity categoriaEntity : entities ) {
            list.add( toModel( categoriaEntity ) );
        }

        return list;
    }

    @Override
    public List<CategoriaEntity> toEntityList(List<CategoriaModel> models) {
        if ( models == null ) {
            return null;
        }

        List<CategoriaEntity> list = new ArrayList<CategoriaEntity>( models.size() );
        for ( CategoriaModel categoriaModel : models ) {
            list.add( toEntity( categoriaModel ) );
        }

        return list;
    }
}
