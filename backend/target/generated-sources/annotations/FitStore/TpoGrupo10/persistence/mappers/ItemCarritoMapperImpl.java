package FitStore.TpoGrupo10.persistence.mappers;

import FitStore.TpoGrupo10.models.ItemCarritoModel;
import FitStore.TpoGrupo10.persistence.entities.ItemCarritoEmbeddable;
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
public class ItemCarritoMapperImpl implements ItemCarritoMapper {

    @Override
    public ItemCarritoModel toModel(ItemCarritoEmbeddable embeddable) {
        if ( embeddable == null ) {
            return null;
        }

        ItemCarritoModel itemCarritoModel = new ItemCarritoModel();

        if ( embeddable.getProductoId() != null ) {
            itemCarritoModel.setProductoId( String.valueOf( embeddable.getProductoId() ) );
        }
        itemCarritoModel.setCantidad( embeddable.getCantidad() );

        return itemCarritoModel;
    }

    @Override
    public ItemCarritoEmbeddable toEntity(ItemCarritoModel model) {
        if ( model == null ) {
            return null;
        }

        ItemCarritoEmbeddable itemCarritoEmbeddable = new ItemCarritoEmbeddable();

        if ( model.getProductoId() != null ) {
            itemCarritoEmbeddable.setProductoId( Long.parseLong( model.getProductoId() ) );
        }
        itemCarritoEmbeddable.setCantidad( model.getCantidad() );

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
