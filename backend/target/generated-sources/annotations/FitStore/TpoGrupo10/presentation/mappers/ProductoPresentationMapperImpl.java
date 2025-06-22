package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.ProductoCreateDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoUpdateDto;
import FitStore.TpoGrupo10.presentation.dto.response.ProductoResponseDto;
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
public class ProductoPresentationMapperImpl implements ProductoPresentationMapper {

    @Override
    public ProductoModel toModel(ProductoCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductoModel productoModel = new ProductoModel();

        productoModel.setCategory( productoCreateDtoToCategoriaModel( dto ) );
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
    public List<ProductoResponseDto> toResponseDtoList(List<ProductoModel> models) {
        if ( models == null ) {
            return null;
        }

        List<ProductoResponseDto> list = new ArrayList<ProductoResponseDto>( models.size() );
        for ( ProductoModel productoModel : models ) {
            list.add( toResponseDto( productoModel ) );
        }

        return list;
    }

    @Override
    public List<ProductoModel> toModelList(List<ProductoCreateDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ProductoModel> list = new ArrayList<ProductoModel>( dtos.size() );
        for ( ProductoCreateDto productoCreateDto : dtos ) {
            list.add( toModel( productoCreateDto ) );
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
