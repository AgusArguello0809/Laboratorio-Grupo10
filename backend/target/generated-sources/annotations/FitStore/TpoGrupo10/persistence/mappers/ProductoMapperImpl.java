package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.CarritoEntity;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T01:06:22-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoModel toModel(ProductoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductoModel productoModel = new ProductoModel();

        productoModel.setId( entity.getId() );
        productoModel.setTitle( entity.getTitle() );
        productoModel.setDescription( entity.getDescription() );
        productoModel.setStock( entity.getStock() );
        productoModel.setPrice( entity.getPrice() );
        productoModel.setCategory( categoriaEntityToCategoriaModel( entity.getCategory() ) );
        productoModel.setOwner( usuarioEntityToUsuarioModel( entity.getOwner() ) );

        return productoModel;
    }

    @Override
    public ProductoEntity toEntity(ProductoModel model) {
        if ( model == null ) {
            return null;
        }

        ProductoEntity productoEntity = new ProductoEntity();

        productoEntity.setId( model.getId() );
        productoEntity.setTitle( model.getTitle() );
        productoEntity.setDescription( model.getDescription() );
        productoEntity.setStock( model.getStock() );
        productoEntity.setPrice( model.getPrice() );
        productoEntity.setCategory( categoriaModelToCategoriaEntity( model.getCategory() ) );
        productoEntity.setOwner( usuarioModelToUsuarioEntity( model.getOwner() ) );

        return productoEntity;
    }

    @Override
    public List<ProductoModel> toModelList(List<ProductoEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductoModel> list = new ArrayList<ProductoModel>( entities.size() );
        for ( ProductoEntity productoEntity : entities ) {
            list.add( toModel( productoEntity ) );
        }

        return list;
    }

    protected CategoriaModel categoriaEntityToCategoriaModel(CategoriaEntity categoriaEntity) {
        if ( categoriaEntity == null ) {
            return null;
        }

        CategoriaModel categoriaModel = new CategoriaModel();

        categoriaModel.setId( categoriaEntity.getId() );
        categoriaModel.setNombre( categoriaEntity.getNombre() );
        categoriaModel.setProductos( toModelList( categoriaEntity.getProductos() ) );

        return categoriaModel;
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
        carritoModel.setOwner( usuarioEntityToUsuarioModel( carritoEntity.getOwner() ) );
        carritoModel.setProductos( itemCarritoEmbeddableListToItemCarritoModelList( carritoEntity.getProductos() ) );

        return carritoModel;
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
        usuarioModel.setProductos( toModelList( usuarioEntity.getProductos() ) );
        usuarioModel.setCarrito( carritoEntityToCarritoModel( usuarioEntity.getCarrito() ) );

        return usuarioModel;
    }

    protected List<ProductoEntity> productoModelListToProductoEntityList(List<ProductoModel> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductoEntity> list1 = new ArrayList<ProductoEntity>( list.size() );
        for ( ProductoModel productoModel : list ) {
            list1.add( toEntity( productoModel ) );
        }

        return list1;
    }

    protected CategoriaEntity categoriaModelToCategoriaEntity(CategoriaModel categoriaModel) {
        if ( categoriaModel == null ) {
            return null;
        }

        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setId( categoriaModel.getId() );
        categoriaEntity.setNombre( categoriaModel.getNombre() );
        categoriaEntity.setProductos( productoModelListToProductoEntityList( categoriaModel.getProductos() ) );

        return categoriaEntity;
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
        carritoEntity.setOwner( usuarioModelToUsuarioEntity( carritoModel.getOwner() ) );
        carritoEntity.setProductos( itemCarritoModelListToItemCarritoEmbeddableList( carritoModel.getProductos() ) );

        return carritoEntity;
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
        usuarioEntity.setCarrito( carritoModelToCarritoEntity( usuarioModel.getCarrito() ) );

        return usuarioEntity;
    }
}
