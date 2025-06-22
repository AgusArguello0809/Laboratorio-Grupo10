package FitStore.TpoGrupo10.persistence.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsuarioEntity is a Querydsl query type for UsuarioEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsuarioEntity extends EntityPathBase<UsuarioEntity> {

    private static final long serialVersionUID = 1793764462L;

    public static final QUsuarioEntity usuarioEntity = new QUsuarioEntity("usuarioEntity");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<FitStore.TpoGrupo10.persistence.entities.enums.Role> role = createEnum("role", FitStore.TpoGrupo10.persistence.entities.enums.Role.class);

    public final StringPath username = createString("username");

    public QUsuarioEntity(String variable) {
        super(UsuarioEntity.class, forVariable(variable));
    }

    public QUsuarioEntity(Path<? extends UsuarioEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsuarioEntity(PathMetadata metadata) {
        super(UsuarioEntity.class, metadata);
    }

}

