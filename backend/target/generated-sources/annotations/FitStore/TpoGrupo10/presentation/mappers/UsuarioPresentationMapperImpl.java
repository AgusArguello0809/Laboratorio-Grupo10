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
    date = "2025-06-05T01:06:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UsuarioPresentationMapperImpl implements UsuarioPresentationMapper {

    @Autowired
    private ProductoPresentationMapper productoPresentationMapper;

    @Override
    public UsuarioModel toModel(UsuarioDto dto) {
        if ( dto == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setProductos( productoDtoListToProductoModelList( dto.getProductos() ) );
        usuarioModel.setId( dto.getId() );
        usuarioModel.setUsername( dto.getUsername() );
        usuarioModel.setName( dto.getName() );
        usuarioModel.setLastName( dto.getLastName() );
        usuarioModel.setEmail( dto.getEmail() );
        usuarioModel.setPassword( dto.getPassword() );
        usuarioModel.setCarrito( carritoDtoToCarritoModel( dto.getCarrito() ) );

        return usuarioModel;
    }

    @Override
    public UsuarioDto toDto(UsuarioModel model) {
        if ( model == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setProductos( productoPresentationMapper.toDtoList( model.getProductos() ) );
        usuarioDto.setId( model.getId() );
        usuarioDto.setUsername( model.getUsername() );
        usuarioDto.setName( model.getName() );
        usuarioDto.setLastName( model.getLastName() );
        usuarioDto.setEmail( model.getEmail() );
        usuarioDto.setPassword( model.getPassword() );
        usuarioDto.setCarrito( carritoModelToCarritoDto( model.getCarrito() ) );

        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> toDtoList(List<UsuarioModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<UsuarioDto> list = new ArrayList<UsuarioDto>( modelList.size() );
        for ( UsuarioModel usuarioModel : modelList ) {
            list.add( toDto( usuarioModel ) );
        }

        return list;
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
        carritoModel.setOwner( toModel( carritoDto.getOwner() ) );
        carritoModel.setProductos( itemCarritoDtoListToItemCarritoModelList( carritoDto.getProductos() ) );

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

    protected CarritoDto carritoModelToCarritoDto(CarritoModel carritoModel) {
        if ( carritoModel == null ) {
            return null;
        }

        CarritoDto carritoDto = new CarritoDto();

        carritoDto.setId( carritoModel.getId() );
        carritoDto.setOwner( toDto( carritoModel.getOwner() ) );
        carritoDto.setProductos( itemCarritoModelListToItemCarritoDtoList( carritoModel.getProductos() ) );

        return carritoDto;
    }
}
