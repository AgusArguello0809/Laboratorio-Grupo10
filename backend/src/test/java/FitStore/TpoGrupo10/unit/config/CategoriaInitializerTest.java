package FitStore.TpoGrupo10.unit.config;
import FitStore.TpoGrupo10.config.CategoriaInitializer;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CategoriaInitializerTest {

    private CategoriaRepository categoriaRepository;
    private CategoriaInitializer initializer;

    @BeforeEach
    void setUp() {
        categoriaRepository = mock(CategoriaRepository.class);
        initializer = new CategoriaInitializer(categoriaRepository);
    }

    @Test
    void initCategorias_cuandoNoHayCategorias_guardaCincoCategorias() {
        when(categoriaRepository.count()).thenReturn(0L);

        initializer.initCategorias();

        verify(categoriaRepository).saveAll(argThat(iterable -> {
            List<CategoriaModel> lista = new ArrayList<>();
            iterable.forEach(lista::add);

            return lista.size() == 5 &&
                    lista.stream()
                            .map(CategoriaModel::getNombre)
                            .toList()
                            .containsAll(List.of("Calzado", "Equipamiento", "Ropa", "Suplementos", "Accesorios"));
        }));
    }

    @Test
    void initCategorias_cuandoYaHayCategorias_noHaceNada() {
        when(categoriaRepository.count()).thenReturn(10L);

        initializer.initCategorias();

        verify(categoriaRepository, never()).saveAll(any());
    }
}
