package FitStore.TpoGrupo10.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FitStore API",
                version = "1.0",
                description = "API REST para el e-commerce desarrollado en el TPO de Aplicaciones Interactivas - UADE"
        )
)
public class OpenApiConfig {
}
