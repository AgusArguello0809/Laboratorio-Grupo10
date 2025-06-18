package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.ProductoCreateDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoResponseDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoUpdateDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-17T20:56:42-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ProductoPresentationMapperImpl implements ProductoPresentationMapper {

    @Override
    public ProductoModel toModel(ProductoCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductoModel productoModel = new ProductoModel();

        productoModel.setCategory( productoCreateDtoToCategoriaModel( dto ) );
        productoModel.setOwner( productoCreateDtoToUsuarioModel( dto ) );
        productoModel.setTitle( dto.getTitle() );
        productoModel.setDescription( dto.getDescription() );
        productoModel.setStock( dto.getStock() );
        productoModel.setPrice( dto.getPrice() );

        return productoModel;
    }

    @Override
    public ProductoModel toModel(ProductoUpdateDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductoModel productoModel = new ProductoModel();

        productoModel.setCategory( productoUpdateDtoToCategoriaModel( dto ) );
        productoModel.setTitle( dto.getTitle() );
        productoModel.setDescription( dto.getDescription() );
        productoModel.setStock( dto.getStock() );
        productoModel.setPrice( dto.getPrice() );

        return productoModel;
    }

    @Override
    public ProductoResponseDto toResponseDto(ProductoModel model) {
        if ( model == null ) {
            return null;
        }

        ProductoResponseDto productoResponseDto = new ProductoResponseDto();

        productoResponseDto.setId( model.getId() );
        productoResponseDto.setCategoryId( modelCategoryId( model ) );
        productoResponseDto.setOwnerId( modelOwnerId( model ) );
        List<String> list = model.getImages();
        if ( list != null ) {
            productoResponseDto.setImages( new ArrayList<String>( list ) );
        }
        productoResponseDto.setTitle( model.getTitle() );
        productoResponseDto.setDescription( model.getDescription() );
        productoResponseDto.setStock( model.getStock() );
        productoResponseDto.setPrice( model.getPrice() );

        return productoResponseDto;
    }

    @Override
    public List<ProductoResponseDto> toResponseDtoList(List<ProductoModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<ProductoResponseDto> list = new ArrayList<ProductoResponseDto>( modelList.size() );
        for ( ProductoModel productoModel : modelList ) {
            list.add( toResponseDto( productoModel ) );
        }

        return list;
    }

    protected CategoriaModel productoCreateDtoToCategoriaModel(ProductoCreateDto productoCreateDto) {
        if ( productoCreateDto == null ) {
            return null;
        }

        CategoriaModel categoriaModel = new CategoriaModel();

        categoriaModel.setId( productoCreateDto.getCategoryId() );

        return categoriaModel;
    }

    protected UsuarioModel productoCreateDtoToUsuarioModel(ProductoCreateDto productoCreateDto) {
        if ( productoCreateDto == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId( productoCreateDto.getOwnerId() );

        return usuarioModel;
    }

    protected CategoriaModel productoUpdateDtoToCategoriaModel(ProductoUpdateDto productoUpdateDto) {
        if ( productoUpdateDto == null ) {
            return null;
        }

        CategoriaModel categoriaModel = new CategoriaModel();

        categoriaModel.setId( productoUpdateDto.getCategoryId() );

        return categoriaModel;
    }

    private Long modelCategoryId(ProductoModel productoModel) {
        if ( productoModel == null ) {
            return null;
        }
        CategoriaModel category = productoModel.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long modelOwnerId(ProductoModel productoModel) {
        if ( productoModel == null ) {
            return null;
        }
        UsuarioModel owner = productoModel.getOwner();
        if ( owner == null ) {
            return null;
        }
        Long id = owner.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
