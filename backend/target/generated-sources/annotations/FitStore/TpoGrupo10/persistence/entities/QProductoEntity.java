package FitStore.TpoGrupo10.persistence.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductoEntity is a Querydsl query type for ProductoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductoEntity extends EntityPathBase<ProductoEntity> {

    private static final long serialVersionUID = -46265018L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductoEntity productoEntity = new QProductoEntity("productoEntity");

    public final QCategoriaEntity category;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> images = this.<String, StringPath>createList("images", String.class, StringPath.class, PathInits.DIRECT2);

    public final QUsuarioEntity owner;

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final StringPath title = createString("title");

    public QProductoEntity(String variable) {
        this(ProductoEntity.class, forVariable(variable), INITS);
    }

    public QProductoEntity(Path<? extends ProductoEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductoEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductoEntity(PathMetadata metadata, PathInits inits) {
        this(ProductoEntity.class, metadata, inits);
    }

    public QProductoEntity(Class<? extends ProductoEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategoriaEntity(forProperty("category")) : null;
        this.owner = inits.isInitialized("owner") ? new QUsuarioEntity(forProperty("owner")) : null;
    }

}

