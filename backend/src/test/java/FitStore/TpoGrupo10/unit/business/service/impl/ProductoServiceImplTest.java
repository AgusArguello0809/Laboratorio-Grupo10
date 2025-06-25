package FitStore.TpoGrupo10.unit.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private void mockSecurityContext(String username) {
        var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

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
        productoModel.setImages(new ArrayList<>());
        productoModel.setOwner(new UsuarioModel());

        categoriaModel = new CategoriaModel();
        categoriaModel.setId(1L);

        usuarioModel = new UsuarioModel();
        usuarioModel.setUsername("testuser");
    }

    @Test
    void save_productoValido_retornaProductoGuardado() throws Exception {
        MockMultipartFile archivo = new MockMultipartFile("file", "imagen.jpg", "image/jpeg", "contenido".getBytes());
        MultipartFile[] archivos = new MultipartFile[]{archivo};

        productoModel.setCategory(categoriaModel);

        when(categoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoriaModel));
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuarioModel));
        when(storageService.uploadFile(any())).thenReturn("https://mockurl.com/imagen.jpg");
        when(productoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProductoModel resultado = productoService.save(productoModel, "testuser", archivos);

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

    @Test
    void findById_productoExiste_retornaProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoModel));
        ProductoModel resultado = productoService.findById(1L);
        assertEquals(productoModel, resultado);
    }

    @Test
    void findById_productoNoExiste_lanzaExcepcion() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        BusinessException ex = assertThrows(BusinessException.class, () -> productoService.findById(1L));
        assertEquals(ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO.getMessage() + ", id: 1", ex.getMessage());
    }

    @Test
    void actualizarPrecioYStock_actualizaCorrectamente() {
        productoModel.setPrice(100);
        productoModel.setStock(5);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoModel));
        when(productoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ProductoModel actualizado = productoService.actualizarPrecioYStock(1L, 150, 8);
        assertEquals(150, actualizado.getPrice());
        assertEquals(8, actualizado.getStock());
    }

    @Test
    void eliminarImagenes_eliminaCorrectamente() {
        productoModel.setImages(List.of("img1", "img2", "img3"));
        productoModel.setOwner(usuarioModel);
        usuarioModel.setUsername("testuser");
        mockSecurityContext("testuser");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoModel));
        when(productoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ProductoModel resultado = productoService.eliminarImagenes(1L, List.of("img2"));
        assertEquals(List.of("img1", "img3"), resultado.getImages());
    }

    @Test
    void agregarImagenes_productoValido_agregaCorrectamente() throws Exception {
        productoModel.setImages(new ArrayList<>(List.of("img1")));
        productoModel.setOwner(usuarioModel);
        usuarioModel.setUsername("testuser");
        mockSecurityContext("testuser");

        MockMultipartFile file = new MockMultipartFile("file", "img2.jpg", "image/jpeg", "contenido".getBytes());

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoModel));
        when(storageService.uploadFile(any())).thenReturn("img2");
        when(productoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ProductoModel resultado = productoService.agregarImagenes(1L, new MultipartFile[]{file});
        assertEquals(2, resultado.getImages().size());
        assertEquals("img2", resultado.getImages().get(1));
    }

    @Test
    void agregarImagenes_excedeLimite_lanzaExcepcion() {
        List<String> imagenes = new ArrayList<>();
        for (int i = 0; i < 10; i++) imagenes.add("img" + i);

        productoModel.setImages(imagenes);
        productoModel.setOwner(usuarioModel);
        usuarioModel.setUsername("testuser");
        mockSecurityContext("testuser");

        MockMultipartFile file = new MockMultipartFile("file", "img.jpg", "image/jpeg", "contenido".getBytes());
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoModel));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                productoService.agregarImagenes(1L, new MultipartFile[]{file})
        );

        assertEquals(ErrorCodeEnum.IMAGENES_EXCEDIDAS.getMessage(), ex.getMessage());
    }

    @Test
    void delete_productoNoExiste_lanzaExcepcion() {
        when(productoRepository.existsById(1L)).thenReturn(false);
        BusinessException ex = assertThrows(BusinessException.class, () -> productoService.delete(1L));
        assertEquals(ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO.getMessage() + ", id: 1", ex.getMessage());
    }

    @Test
    void delete_productoSinPermiso_lanzaExcepcion() {
        ProductoModel producto = new ProductoModel();
        UsuarioModel otroUsuario = new UsuarioModel();
        otroUsuario.setUsername("otro");
        producto.setOwner(otroUsuario);

        when(productoRepository.existsById(1L)).thenReturn(true);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        mockSecurityContext("alguien");

        assertThrows(FitStore.TpoGrupo10.security.exception.SecurityException.class, () ->
                productoService.delete(1L)
        );
    }
}