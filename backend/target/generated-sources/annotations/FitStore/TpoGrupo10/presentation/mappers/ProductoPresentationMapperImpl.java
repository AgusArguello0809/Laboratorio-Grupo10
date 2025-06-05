package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.dto.CategoriaDto;
import FitStore.TpoGrupo10.presentation.dto.ItemCarritoDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T01:05:31-0300",
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

    protected ItemCarritoDto itemCarritoModelToItemCarritoDto(ItemCarritoModel itemCarritoModel) {
        if ( itemCarritoModel == null ) {
            return null;
        }

        ItemCarritoDto itemCarritoDto = new ItemCarritoDto();

        itemCarritoDto.setProductoId( itemCarritoModel.getProductoId() );
        itemCarritoDto.setCantidad( itemCarritoModel.getCantidad() );

        return itemCarritoDto;
    }

    protected List<ItemCarritoDto> itemCarritoModelListToItemCarritoDtoList(List<ItemCarritoModel> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCarritoDto> list1 = new ArrayList<ItemCarritoDto>( list.size() );
        for ( ItemCarritoModel itemCarritoModel : list ) {
            list1.add( itemCarritoModelToItemCarritoDto( itemCarritoModel ) );
        }

        return list1;
    }

    protected CarritoDto carritoModelToCarritoDto(CarritoModel carritoModel) {
        if ( carritoModel == null ) {
            return null;
        }

        CarritoDto carritoDto = new CarritoDto();

        carritoDto.setId( carritoModel.getId() );
        carritoDto.setOwner( usuarioModelToUsuarioDto( carritoModel.getOwner() ) );
        carritoDto.setProductos( itemCarritoModelListToItemCarritoDtoList( carritoModel.getProductos() ) );

        return carritoDto;
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
        usuarioDto.setProductos( toDtoList( usuarioModel.getProductos() ) );
        usuarioDto.setCarrito( carritoModelToCarritoDto( usuarioModel.getCarrito() ) );

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

    protected List<ProductoModel> productoDtoListToProductoModelList(List<ProductoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductoModel> list1 = new ArrayList<ProductoModel>( list.size() );
        for ( ProductoDto productoDto : list ) {
            list1.add( toModel( productoDto ) );
        }

        return list1;
    }

    protected ItemCarritoModel itemCarritoDtoToItemCarritoModel(ItemCarritoDto itemCarritoDto) {
        if ( itemCarritoDto == null ) {
            return null;
        }

        ItemCarritoModel itemCarritoModel = new ItemCarritoModel();

        itemCarritoModel.setProductoId( itemCarritoDto.getProductoId() );
        itemCarritoModel.setCantidad( itemCarritoDto.getCantidad() );

        return itemCarritoModel;
    }

    protected List<ItemCarritoModel> itemCarritoDtoListToItemCarritoModelList(List<ItemCarritoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCarritoModel> list1 = new ArrayList<ItemCarritoModel>( list.size() );
        for ( ItemCarritoDto itemCarritoDto : list ) {
            list1.add( itemCarritoDtoToItemCarritoModel( itemCarritoDto ) );
        }

        return list1;
    }

    protected CarritoModel carritoDtoToCarritoModel(CarritoDto carritoDto) {
        if ( carritoDto == null ) {
            return null;
        }

        CarritoModel carritoModel = new CarritoModel();

        carritoModel.setId( carritoDto.getId() );
        carritoModel.setOwner( usuarioDtoToUsuarioModel( carritoDto.getOwner() ) );
        carritoModel.setProductos( itemCarritoDtoListToItemCarritoModelList( carritoDto.getProductos() ) );

        return carritoModel;
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
        usuarioModel.setProductos( productoDtoListToProductoModelList( usuarioDto.getProductos() ) );
        usuarioModel.setCarrito( carritoDtoToCarritoModel( usuarioDto.getCarrito() ) );

        return usuarioModel;
    }
}
