package FitStore.TpoGrupo10.unit.business.service.impl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.business.service.FirebaseStorageService;
import FitStore.TpoGrupo10.business.service.impl.ProductoServiceImpl;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;

class ProductoServiceImplTest {

    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;
    private UsuarioRepository usuarioRepository;
    private FirebaseStorageService storageService;
    private ProductoServiceImpl productoService;

    private ProductoModel productoModel;
    private CategoriaModel categoriaModel;
    private UsuarioModel usuarioModel;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        categoriaRepository = mock(CategoriaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        storageService = mock(FirebaseStorageService.class);
        productoService = new ProductoServiceImpl(productoRepository, categoriaRepository, usuarioRepository, storageService);

        productoModel = new ProductoModel();
        productoModel.setTitle("Producto Test");
        productoModel.setCategory(new CategoriaModel());
        productoModel.setImages(List.of());
        productoModel.setOwner(new UsuarioModel());

        categoriaModel = new CategoriaModel();
        categoriaModel.setId(1L);

        usuarioModel = new UsuarioModel();
        usuarioModel.setUsername("testuser");
    }

    @Test
    void save_productoValido_retornaProductoGuardado() {
        // Arrange
        MockMultipartFile archivo = new MockMultipartFile("file", "imagen.jpg", "image/jpeg", "contenido".getBytes());
        MultipartFile[] archivos = new MultipartFile[]{archivo};

        productoModel.setCategory(categoriaModel);

        when(categoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoriaModel));
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuarioModel));
        try {
            when(storageService.uploadFile(any())).thenReturn("https://mockurl.com/imagen.jpg");
        } catch (Exception e) {
            fail("Exception should not be thrown in mock setup: " + e.getMessage());
        }
        when(productoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProductoModel resultado = productoService.save(productoModel, "testuser", archivos);

        // Assert
        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getTitle());
        assertEquals(1, resultado.getImages().size());
        verify(productoRepository).save(any());
    }

    @Test
    void save_sinImagenes_lanzaExcepcion() {
        productoModel.setCategory(categoriaModel);
        when(categoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoriaModel));
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuarioModel));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                productoService.save(productoModel, "testuser", new MultipartFile[]{}));

        assertEquals(ErrorCodeEnum.IMAGENES_OBLIGATORIAS.getMessage(), exception.getMessage());
    }
}
