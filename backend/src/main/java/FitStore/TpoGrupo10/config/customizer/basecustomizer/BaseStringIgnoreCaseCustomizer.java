package FitStore.TpoGrupo10.config.customizer.basecustomizer;

import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;


public class BaseStringIgnoreCaseCustomizer<T extends EntityPathBase<?>> implements QuerydslBinderCustomizer<T> {

    @Override
    public void customize(QuerydslBindings bindings, T root) {
        bindings.bind(String.class)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
