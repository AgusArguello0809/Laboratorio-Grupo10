package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
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

        return categoriaModel;
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

        return usuarioModel;
    }

    protected CategoriaEntity categoriaModelToCategoriaEntity(CategoriaModel categoriaModel) {
        if ( categoriaModel == null ) {
            return null;
        }

        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setId( categoriaModel.getId() );
        categoriaEntity.setNombre( categoriaModel.getNombre() );

        return categoriaEntity;
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

        return usuarioEntity;
    }
}
