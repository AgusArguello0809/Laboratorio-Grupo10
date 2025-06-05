package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T19:02:52-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CategoriaMapperImpl implements CategoriaMapper {

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
}
