package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-20T15:51:25-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class ItemCarritoPersistenceMapperImpl implements ItemCarritoPersistenceMapper {

    @Override
    public ItemCarritoModel toModel(ItemCarritoEmbeddable embeddable) {
        if ( embeddable == null ) {
            return null;
        }

        ItemCarritoModel itemCarritoModel = new ItemCarritoModel();

        itemCarritoModel.setProductoId( embeddable.getProductoId() );
        if ( embeddable.getCantidad() != null ) {
            itemCarritoModel.setCantidad( embeddable.getCantidad() );
        }
        if ( embeddable.getPrecioUnitario() != null ) {
            itemCarritoModel.setPrecioUnitario( embeddable.getPrecioUnitario() );
        }
        if ( embeddable.getSubTotal() != null ) {
            itemCarritoModel.setSubTotal( embeddable.getSubTotal() );
        }

        return itemCarritoModel;
    }

    @Override
    public ItemCarritoEmbeddable toEntity(ItemCarritoModel model) {
        if ( model == null ) {
            return null;
        }

        ItemCarritoEmbeddable itemCarritoEmbeddable = new ItemCarritoEmbeddable();

        itemCarritoEmbeddable.setProductoId( model.getProductoId() );
        itemCarritoEmbeddable.setCantidad( model.getCantidad() );
        itemCarritoEmbeddable.setPrecioUnitario( model.getPrecioUnitario() );
        itemCarritoEmbeddable.setSubTotal( model.getSubTotal() );

        return itemCarritoEmbeddable;
    }

    @Override
    public List<ItemCarritoModel> toModelList(List<ItemCarritoEmbeddable> embeddables) {
        if ( embeddables == null ) {
            return null;
        }

        List<ItemCarritoModel> list = new ArrayList<ItemCarritoModel>( embeddables.size() );
        for ( ItemCarritoEmbeddable itemCarritoEmbeddable : embeddables ) {
            list.add( toModel( itemCarritoEmbeddable ) );
        }

        return list;
    }

    @Override
    public List<ItemCarritoEmbeddable> toEntityList(List<ItemCarritoModel> models) {
        if ( models == null ) {
            return null;
        }

        List<ItemCarritoEmbeddable> list = new ArrayList<ItemCarritoEmbeddable>( models.size() );
        for ( ItemCarritoModel itemCarritoModel : models ) {
            list.add( toEntity( itemCarritoModel ) );
        }

        return list;
    }
}
