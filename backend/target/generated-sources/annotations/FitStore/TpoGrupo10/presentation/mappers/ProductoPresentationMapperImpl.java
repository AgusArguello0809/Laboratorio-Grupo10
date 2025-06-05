package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.CategoriaDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
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
public class ProductoPresentationMapperImpl implements ProductoPresentationMapper {

    @Override
    public ProductoDto toDto(ProductoModel model) {
        if ( model == null ) {
            return null;
        }

        ProductoDto productoDto = new ProductoDto();

        productoDto.setId( model.getId() );
        productoDto.setTitle( model.getTitle() );
        productoDto.setDescription( model.getDescription() );
        productoDto.setStock( model.getStock() );
        productoDto.setPrice( model.getPrice() );
        productoDto.setCategory( categoriaModelToCategoriaDto( model.getCategory() ) );
        productoDto.setOwner( usuarioModelToUsuarioDto( model.getOwner() ) );

        return productoDto;
    }

    @Override
    public ProductoModel toModel(ProductoDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductoModel productoModel = new ProductoModel();

        productoModel.setId( dto.getId() );
        productoModel.setTitle( dto.getTitle() );
        productoModel.setDescription( dto.getDescription() );
        productoModel.setStock( dto.getStock() );
        productoModel.setPrice( dto.getPrice() );
        productoModel.setCategory( categoriaDtoToCategoriaModel( dto.getCategory() ) );
        productoModel.setOwner( usuarioDtoToUsuarioModel( dto.getOwner() ) );

        return productoModel;
    }

    @Override
    public List<ProductoDto> toDtoList(List<ProductoModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<ProductoDto> list = new ArrayList<ProductoDto>( modelList.size() );
        for ( ProductoModel productoModel : modelList ) {
            list.add( toDto( productoModel ) );
        }

        return list;
    }

    protected CategoriaDto categoriaModelToCategoriaDto(CategoriaModel categoriaModel) {
        if ( categoriaModel == null ) {
            return null;
        }

        CategoriaDto categoriaDto = new CategoriaDto();

        if ( categoriaModel.getId() != null ) {
            categoriaDto.setId( String.valueOf( categoriaModel.getId() ) );
        }
        categoriaDto.setNombre( categoriaModel.getNombre() );

        return categoriaDto;
    }

    protected UsuarioDto usuarioModelToUsuarioDto(UsuarioModel usuarioModel) {
        if ( usuarioModel == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId( usuarioModel.getId() );
        usuarioDto.setUsername( usuarioModel.getUsername() );
        usuarioDto.setName( usuarioModel.getName() );
        usuarioDto.setLastName( usuarioModel.getLastName() );
        usuarioDto.setEmail( usuarioModel.getEmail() );
        usuarioDto.setPassword( usuarioModel.getPassword() );

        return usuarioDto;
    }

    protected CategoriaModel categoriaDtoToCategoriaModel(CategoriaDto categoriaDto) {
        if ( categoriaDto == null ) {
            return null;
        }

        CategoriaModel categoriaModel = new CategoriaModel();

        if ( categoriaDto.getId() != null ) {
            categoriaModel.setId( Long.parseLong( categoriaDto.getId() ) );
        }
        categoriaModel.setNombre( categoriaDto.getNombre() );

        return categoriaModel;
    }

    protected UsuarioModel usuarioDtoToUsuarioModel(UsuarioDto usuarioDto) {
        if ( usuarioDto == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId( usuarioDto.getId() );
        usuarioModel.setUsername( usuarioDto.getUsername() );
        usuarioModel.setName( usuarioDto.getName() );
        usuarioModel.setLastName( usuarioDto.getLastName() );
        usuarioModel.setEmail( usuarioDto.getEmail() );
        usuarioModel.setPassword( usuarioDto.getPassword() );

        return usuarioModel;
    }
}
