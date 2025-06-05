package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;
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
public class UsuarioMapperImpl implements UsuarioMapper {

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public UsuarioModel toModel(UsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setProductos( productoMapper.toModelList( entity.getProductos() ) );
        usuarioModel.setId( entity.getId() );
        usuarioModel.setUsername( entity.getUsername() );
        usuarioModel.setName( entity.getName() );
        usuarioModel.setLastName( entity.getLastName() );
        usuarioModel.setEmail( entity.getEmail() );
        usuarioModel.setPassword( entity.getPassword() );
        usuarioModel.setCarrito( carritoEntityToCarritoModel( entity.getCarrito() ) );

        return usuarioModel;
    }

    @Override
    public UsuarioEntity toEntity(UsuarioModel model) {
        if ( model == null ) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setProductos( productoModelListToProductoEntityList( model.getProductos() ) );
        usuarioEntity.setId( model.getId() );
        usuarioEntity.setUsername( model.getUsername() );
        usuarioEntity.setName( model.getName() );
        usuarioEntity.setLastName( model.getLastName() );
        usuarioEntity.setEmail( model.getEmail() );
        usuarioEntity.setPassword( model.getPassword() );
        usuarioEntity.setCarrito( carritoModelToCarritoEntity( model.getCarrito() ) );

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

    protected ItemCarritoModel itemCarritoEmbeddableToItemCarritoModel(ItemCarritoEmbeddable itemCarritoEmbeddable) {
        if ( itemCarritoEmbeddable == null ) {
            return null;
        }

        ItemCarritoModel itemCarritoModel = new ItemCarritoModel();

        if ( itemCarritoEmbeddable.getProductoId() != null ) {
            itemCarritoModel.setProductoId( String.valueOf( itemCarritoEmbeddable.getProductoId() ) );
        }
        itemCarritoModel.setCantidad( itemCarritoEmbeddable.getCantidad() );

        return itemCarritoModel;
    }

    protected List<ItemCarritoModel> itemCarritoEmbeddableListToItemCarritoModelList(List<ItemCarritoEmbeddable> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCarritoModel> list1 = new ArrayList<ItemCarritoModel>( list.size() );
        for ( ItemCarritoEmbeddable itemCarritoEmbeddable : list ) {
            list1.add( itemCarritoEmbeddableToItemCarritoModel( itemCarritoEmbeddable ) );
        }

        return list1;
    }

    protected CarritoModel carritoEntityToCarritoModel(CarritoEntity carritoEntity) {
        if ( carritoEntity == null ) {
            return null;
        }

        CarritoModel carritoModel = new CarritoModel();

        carritoModel.setId( carritoEntity.getId() );
        carritoModel.setOwner( toModel( carritoEntity.getOwner() ) );
        carritoModel.setProductos( itemCarritoEmbeddableListToItemCarritoModelList( carritoEntity.getProductos() ) );

        return carritoModel;
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

    protected ItemCarritoEmbeddable itemCarritoModelToItemCarritoEmbeddable(ItemCarritoModel itemCarritoModel) {
        if ( itemCarritoModel == null ) {
            return null;
        }

        ItemCarritoEmbeddable itemCarritoEmbeddable = new ItemCarritoEmbeddable();

        if ( itemCarritoModel.getProductoId() != null ) {
            itemCarritoEmbeddable.setProductoId( Long.parseLong( itemCarritoModel.getProductoId() ) );
        }
        itemCarritoEmbeddable.setCantidad( itemCarritoModel.getCantidad() );

        return itemCarritoEmbeddable;
    }

    protected List<ItemCarritoEmbeddable> itemCarritoModelListToItemCarritoEmbeddableList(List<ItemCarritoModel> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCarritoEmbeddable> list1 = new ArrayList<ItemCarritoEmbeddable>( list.size() );
        for ( ItemCarritoModel itemCarritoModel : list ) {
            list1.add( itemCarritoModelToItemCarritoEmbeddable( itemCarritoModel ) );
        }

        return list1;
    }

    protected CarritoEntity carritoModelToCarritoEntity(CarritoModel carritoModel) {
        if ( carritoModel == null ) {
            return null;
        }

        CarritoEntity carritoEntity = new CarritoEntity();

        carritoEntity.setId( carritoModel.getId() );
        carritoEntity.setOwner( toEntity( carritoModel.getOwner() ) );
        carritoEntity.setProductos( itemCarritoModelListToItemCarritoEmbeddableList( carritoModel.getProductos() ) );

        return carritoEntity;
    }
}
