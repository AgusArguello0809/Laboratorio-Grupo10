package FitStore.TpoGrupo10.unit.business.service.impl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import FitStore.TpoGrupo10.business.service.impl.CategoriaServiceImpl;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;

class CategoriaServiceImplTest {

    private CategoriaRepository categoriaRepository;
    private CategoriaServiceImpl categoriaService;

    @BeforeEach
    void setUp() {
        categoriaRepository = mock(CategoriaRepository.class);
        categoriaService = new CategoriaServiceImpl(categoriaRepository);
    }

    @Test
    void findAll_deberiaRetornarPaginaDeCategorias() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2);
        CategoriaModel cat1 = new CategoriaModel();
        cat1.setId(1L);
        cat1.setNombre("Proteínas");

        CategoriaModel cat2 = new CategoriaModel();
        cat2.setId(2L);
        cat2.setNombre("Accesorios");

        List<CategoriaModel> categorias = List.of(cat1, cat2);
        Page<CategoriaModel> paginaEsperada = new PageImpl<>(categorias, pageable, categorias.size());

        when(categoriaRepository.findAll(pageable)).thenReturn(paginaEsperada);

        // Act
        Page<CategoriaModel> resultado = categoriaService.findAll(pageable);

        // Assert
        assertEquals(2, resultado.getContent().size());
        assertEquals("Proteínas", resultado.getContent().get(0).getNombre());
        verify(categoriaRepository).findAll(pageable);
    }
}