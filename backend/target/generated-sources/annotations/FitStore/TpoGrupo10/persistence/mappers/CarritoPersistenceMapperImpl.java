package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-20T15:51:27-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class CarritoPersistenceMapperImpl implements CarritoPersistenceMapper {

    @Autowired
    private ItemCarritoPersistenceMapper itemCarritoPersistenceMapper;

    @Override
    public CarritoModel toModel(CarritoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CarritoModel carritoModel = new CarritoModel();

        carritoModel.setProductos( itemCarritoPersistenceMapper.toModelList( entity.getProductos() ) );
        carritoModel.setId( entity.getId() );
        carritoModel.setOwner( usuarioEntityToUsuarioModel( entity.getOwner() ) );
        carritoModel.setTotal( entity.getTotal() );

        return carritoModel;
    }

    @Override
    public CarritoEntity toEntity(CarritoModel model) {
        if ( model == null ) {
            return null;
        }

        CarritoEntity carritoEntity = new CarritoEntity();

        carritoEntity.setProductos( itemCarritoPersistenceMapper.toEntityList( model.getProductos() ) );
        carritoEntity.setId( model.getId() );
        carritoEntity.setOwner( usuarioModelToUsuarioEntity( model.getOwner() ) );
        carritoEntity.setTotal( model.getTotal() );

        return carritoEntity;
    }

    protected UsuarioModel usuarioEntityToUsuarioModel(UsuarioEntity usuarioEntity) {
        if ( usuarioEntity == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId( usuarioEntity.getId() );
        usuarioModel.setUsername( usuarioEntity.getUsername() );
        usuarioModel.setName( usuarioEntity.getName() );
        usuarioModel.setLastName( usuarioEntity.getLastName() );
        usuarioModel.setEmail( usuarioEntity.getEmail() );
        usuarioModel.setPassword( usuarioEntity.getPassword() );
        usuarioModel.setRole( usuarioEntity.getRole() );

        return usuarioModel;
    }

    protected UsuarioEntity usuarioModelToUsuarioEntity(UsuarioModel usuarioModel) {
        if ( usuarioModel == null ) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setId( usuarioModel.getId() );
        usuarioEntity.setUsername( usuarioModel.getUsername() );
        usuarioEntity.setName( usuarioModel.getName() );
        usuarioEntity.setLastName( usuarioModel.getLastName() );
        usuarioEntity.setEmail( usuarioModel.getEmail() );
        usuarioEntity.setPassword( usuarioModel.getPassword() );
        usuarioEntity.setRole( usuarioModel.getRole() );

        return usuarioEntity;
    }
}
