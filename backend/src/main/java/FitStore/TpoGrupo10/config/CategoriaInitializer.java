package FitStore.TpoGrupo10.config;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaInitializer {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaInitializer.class);

    private final CategoriaRepository categoriaRepository;

    public CategoriaInitializer(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostConstruct
    public void initCategorias() {
        if (categoriaRepository.count() == 0) {
            List<CategoriaModel> categorias = List.of(
                    crearCategoria("Calzado"),
                    crearCategoria("Equipamiento"),
                    crearCategoria("Ropa"),
                    crearCategoria("Suplementos"),
                    crearCategoria("Accesorios")
            );

            categoriaRepository.saveAll(categorias);
            logger.info("Categorias precargadas en la base de datos.");
        } else {
            logger.info("Las categorias ya estaban cargadas, no se insertaron duplicados.");
        }
    }

    private CategoriaModel crearCategoria(String nombre) {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre(nombre);
        return categoria;
    }
}
