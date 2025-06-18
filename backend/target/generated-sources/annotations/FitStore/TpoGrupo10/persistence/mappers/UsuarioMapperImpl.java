package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-17T20:56:41-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioModel toModel(UsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId( entity.getId() );
        usuarioModel.setUsername( entity.getUsername() );
        usuarioModel.setName( entity.getName() );
        usuarioModel.setLastName( entity.getLastName() );
        usuarioModel.setEmail( entity.getEmail() );
        usuarioModel.setPassword( entity.getPassword() );

        return usuarioModel;
    }

    @Override
    public UsuarioEntity toEntity(UsuarioModel model) {
        if ( model == null ) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setId( model.getId() );
        usuarioEntity.setUsername( model.getUsername() );
        usuarioEntity.setName( model.getName() );
        usuarioEntity.setLastName( model.getLastName() );
        usuarioEntity.setEmail( model.getEmail() );
        usuarioEntity.setPassword( model.getPassword() );

        return usuarioEntity;
    }

    @Override
    public List<UsuarioModel> toModelList(List<UsuarioEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UsuarioModel> list = new ArrayList<UsuarioModel>( entities.size() );
        for ( UsuarioEntity usuarioEntity : entities ) {
            list.add( toModel( usuarioEntity ) );
        }

        return list;
    }
}
