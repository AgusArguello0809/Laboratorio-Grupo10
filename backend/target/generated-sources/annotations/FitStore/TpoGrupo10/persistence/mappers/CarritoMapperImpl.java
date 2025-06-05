package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T01:06:22-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CarritoMapperImpl implements CarritoMapper {

    @Autowired
    private ProductoMapper productoMapper;
    @Autowired
    private ItemCarritoMapper itemCarritoMapper;

    @Override
    public CarritoModel toModel(CarritoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CarritoModel carritoModel = new CarritoModel();

        carritoModel.setProductos( itemCarritoMapper.toModelList( entity.getProductos() ) );
        carritoModel.setId( entity.getId() );
        carritoModel.setOwner( usuarioEntityToUsuarioModel( entity.getOwner() ) );

        return carritoModel;
    }

    @Override
    public CarritoEntity toEntity(CarritoModel model) {
        if ( model == null ) {
            return null;
        }

        CarritoEntity carritoEntity = new CarritoEntity();

        carritoEntity.setProductos( itemCarritoMapper.toEntityList( model.getProductos() ) );
        carritoEntity.setId( model.getId() );
        carritoEntity.setOwner( usuarioModelToUsuarioEntity( model.getOwner() ) );

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
        usuarioModel.setProductos( productoMapper.toModelList( usuarioEntity.getProductos() ) );
        usuarioModel.setCarrito( toModel( usuarioEntity.getCarrito() ) );

        return usuarioModel;
    }

    protected List<ProductoEntity> productoModelListToProductoEntityList(List<ProductoModel> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductoEntity> list1 = new ArrayList<ProductoEntity>( list.size() );
        for ( ProductoModel productoModel : list ) {
            list1.add( productoMapper.toEntity( productoModel ) );
        }

        return list1;
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
        usuarioEntity.setProductos( productoModelListToProductoEntityList( usuarioModel.getProductos() ) );
        usuarioEntity.setCarrito( toEntity( usuarioModel.getCarrito() ) );

        return usuarioEntity;
    }
}
