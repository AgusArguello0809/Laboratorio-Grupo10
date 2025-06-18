package FitStore.TpoGrupo10.persistence.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItemCarritoEmbeddable is a Querydsl query type for ItemCarritoEmbeddable
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QItemCarritoEmbeddable extends BeanPath<ItemCarritoEmbeddable> {

    private static final long serialVersionUID = 2127902165L;

    public static final QItemCarritoEmbeddable itemCarritoEmbeddable = new QItemCarritoEmbeddable("itemCarritoEmbeddable");

    public final NumberPath<Integer> cantidad = createNumber("cantidad", Integer.class);

    public final NumberPath<Double> precioUnitario = createNumber("precioUnitario", Double.class);

    public final NumberPath<Long> productoId = createNumber("productoId", Long.class);

    public final NumberPath<Double> subTotal = createNumber("subTotal", Double.class);

    public QItemCarritoEmbeddable(String variable) {
        super(ItemCarritoEmbeddable.class, forVariable(variable));
    }

    public QItemCarritoEmbeddable(Path<? extends ItemCarritoEmbeddable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItemCarritoEmbeddable(PathMetadata metadata) {
        super(ItemCarritoEmbeddable.class, metadata);
    }

}

