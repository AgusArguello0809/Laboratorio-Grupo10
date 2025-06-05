package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.dto.ItemCarritoDto;
import FitStore.TpoGrupo10.presentation.dto.ProductoDto;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
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
public class CarritoPresentationMapperImpl implements CarritoPresentationMapper {

    @Autowired
    private ProductoPresentationMapper productoPresentationMapper;

    @Override
    public CarritoDto toDto(CarritoModel model) {
        if ( model == null ) {
            return null;
        }

        CarritoDto carritoDto = new CarritoDto();

        carritoDto.setProductos( itemCarritoModelListToItemCarritoDtoList( model.getProductos() ) );
        carritoDto.setId( model.getId() );
        carritoDto.setOwner( usuarioModelToUsuarioDto( model.getOwner() ) );

        return carritoDto;
    }

    @Override
    public CarritoModel toModel(CarritoDto dto) {
        if ( dto == null ) {
            return null;
        }

        CarritoModel carritoModel = new CarritoModel();

        carritoModel.setProductos( itemCarritoDtoListToItemCarritoModelList( dto.getProductos() ) );
        carritoModel.setId( dto.getId() );
        carritoModel.setOwner( usuarioDtoToUsuarioModel( dto.getOwner() ) );

        return carritoModel;
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
        usuarioDto.setProductos( productoPresentationMapper.toDtoList( usuarioModel.getProductos() ) );
        usuarioDto.setCarrito( toDto( usuarioModel.getCarrito() ) );

        return usuarioDto;
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

    protected List<ProductoModel> productoDtoListToProductoModelList(List<ProductoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductoModel> list1 = new ArrayList<ProductoModel>( list.size() );
        for ( ProductoDto productoDto : list ) {
            list1.add( productoPresentationMapper.toModel( productoDto ) );
        }

        return list1;
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
        usuarioModel.setCarrito( toModel( usuarioDto.getCarrito() ) );

        return usuarioModel;
    }
}
