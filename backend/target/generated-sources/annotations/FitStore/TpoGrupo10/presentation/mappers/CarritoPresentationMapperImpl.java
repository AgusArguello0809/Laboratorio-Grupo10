package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.presentation.dto.CarritoDto;
import FitStore.TpoGrupo10.presentation.dto.ItemCarritoDto;
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
public class CarritoPresentationMapperImpl implements CarritoPresentationMapper {

    @Override
    public CarritoDto toDto(CarritoModel model) {
        if ( model == null ) {
            return null;
        }

        CarritoDto carritoDto = new CarritoDto();

        carritoDto.setProductos( itemCarritoModelListToItemCarritoDtoList( model.getProductos() ) );
        carritoDto.setId( model.getId() );
        carritoDto.setTotal( model.getTotal() );

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
        carritoModel.setTotal( dto.getTotal() );

        return carritoModel;
    }

    protected ItemCarritoDto itemCarritoModelToItemCarritoDto(ItemCarritoModel itemCarritoModel) {
        if ( itemCarritoModel == null ) {
            return null;
        }

        ItemCarritoDto itemCarritoDto = new ItemCarritoDto();

        itemCarritoDto.setProductoId( itemCarritoModel.getProductoId() );
        itemCarritoDto.setCantidad( itemCarritoModel.getCantidad() );
        itemCarritoDto.setPrecioUnitario( itemCarritoModel.getPrecioUnitario() );
        itemCarritoDto.setSubTotal( itemCarritoModel.getSubTotal() );

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

    protected ItemCarritoModel itemCarritoDtoToItemCarritoModel(ItemCarritoDto itemCarritoDto) {
        if ( itemCarritoDto == null ) {
            return null;
        }

        ItemCarritoModel itemCarritoModel = new ItemCarritoModel();

        itemCarritoModel.setProductoId( itemCarritoDto.getProductoId() );
        itemCarritoModel.setCantidad( itemCarritoDto.getCantidad() );
        itemCarritoModel.setPrecioUnitario( itemCarritoDto.getPrecioUnitario() );
        itemCarritoModel.setSubTotal( itemCarritoDto.getSubTotal() );

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
}
