package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.presentation.dto.ItemCarritoDto;
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
public class ItemCarritoPresentationMapperImpl implements ItemCarritoPresentationMapper {

    @Override
    public ItemCarritoDto toDto(ItemCarritoModel model) {
        if ( model == null ) {
            return null;
        }

        ItemCarritoDto itemCarritoDto = new ItemCarritoDto();

        itemCarritoDto.setProductoId( model.getProductoId() );
        itemCarritoDto.setCantidad( model.getCantidad() );
        itemCarritoDto.setPrecioUnitario( model.getPrecioUnitario() );
        itemCarritoDto.setSubTotal( model.getSubTotal() );

        return itemCarritoDto;
    }

    @Override
    public ItemCarritoModel toModel(ItemCarritoDto dto) {
        if ( dto == null ) {
            return null;
        }

        ItemCarritoModel itemCarritoModel = new ItemCarritoModel();

        itemCarritoModel.setProductoId( dto.getProductoId() );
        itemCarritoModel.setCantidad( dto.getCantidad() );
        itemCarritoModel.setPrecioUnitario( dto.getPrecioUnitario() );
        itemCarritoModel.setSubTotal( dto.getSubTotal() );

        return itemCarritoModel;
    }

    @Override
    public List<ItemCarritoDto> toDtoList(List<ItemCarritoModel> models) {
        if ( models == null ) {
            return null;
        }

        List<ItemCarritoDto> list = new ArrayList<ItemCarritoDto>( models.size() );
        for ( ItemCarritoModel itemCarritoModel : models ) {
            list.add( toDto( itemCarritoModel ) );
        }

        return list;
    }

    @Override
    public List<ItemCarritoModel> toModelList(List<ItemCarritoDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ItemCarritoModel> list = new ArrayList<ItemCarritoModel>( dtos.size() );
        for ( ItemCarritoDto itemCarritoDto : dtos ) {
            list.add( toModel( itemCarritoDto ) );
        }

        return list;
    }
}
