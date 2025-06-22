package FitStore.TpoGrupo10.persistence.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarritoEntity is a Querydsl query type for CarritoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarritoEntity extends EntityPathBase<CarritoEntity> {

    private static final long serialVersionUID = -710014970L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarritoEntity carritoEntity = new QCarritoEntity("carritoEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUsuarioEntity owner;

    public final ListPath<ItemCarritoEmbeddable, QItemCarritoEmbeddable> productos = this.<ItemCarritoEmbeddable, QItemCarritoEmbeddable>createList("productos", ItemCarritoEmbeddable.class, QItemCarritoEmbeddable.class, PathInits.DIRECT2);

    public final NumberPath<Double> total = createNumber("total", Double.class);

    public QCarritoEntity(String variable) {
        this(CarritoEntity.class, forVariable(variable), INITS);
    }

    public QCarritoEntity(Path<? extends CarritoEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarritoEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarritoEntity(PathMetadata metadata, PathInits inits) {
        this(CarritoEntity.class, metadata, inits);
    }

    public QCarritoEntity(Class<? extends CarritoEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new QUsuarioEntity(forProperty("owner")) : null;
    }

}

