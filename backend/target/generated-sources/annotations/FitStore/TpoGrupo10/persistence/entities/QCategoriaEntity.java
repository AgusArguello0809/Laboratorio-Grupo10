package FitStore.TpoGrupo10.persistence.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoriaEntity is a Querydsl query type for CategoriaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoriaEntity extends EntityPathBase<CategoriaEntity> {

    private static final long serialVersionUID = 82659187L;

    public static final QCategoriaEntity categoriaEntity = new QCategoriaEntity("categoriaEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public QCategoriaEntity(String variable) {
        super(CategoriaEntity.class, forVariable(variable));
    }

    public QCategoriaEntity(Path<? extends CategoriaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoriaEntity(PathMetadata metadata) {
        super(CategoriaEntity.class, metadata);
    }

}

