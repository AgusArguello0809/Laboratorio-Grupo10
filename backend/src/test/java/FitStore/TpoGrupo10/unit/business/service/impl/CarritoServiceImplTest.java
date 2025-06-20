package FitStore.TpoGrupo10.unit.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.business.service.impl.CarritoServiceImpl;
import FitStore.TpoGrupo10.models.*;
import FitStore.TpoGrupo10.persistence.repositories.CarritoRepository;
import FitStore.TpoGrupo10.business.service.ProductoService;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.security.exception.SecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarritoServiceImplTest {

    private CarritoRepository carritoRepository;
    private ProductoService productoService;
    private UsuarioRepository usuarioRepository;
    private CarritoServiceImpl carritoService;

    @BeforeEach
    void setUp() {
        carritoRepository = mock(CarritoRepository.class);
        productoService = mock(ProductoService.class);
        usuarioRepository = mock(UsuarioRepository.class);
        carritoService = new CarritoServiceImpl(carritoRepository, productoService, usuarioRepository);
    }

    private void mockSecurityContext(String username, boolean admin) {
        List<SimpleGrantedAuthority> roles = admin ?
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) :
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, roles)
        );
    }

    @Test
    void getCarritoByOwnerId_carritoEncontrado() {
        UsuarioModel user = new UsuarioModel(); user.setId(1L); user.setUsername("agus");
        CarritoModel carrito = new CarritoModel(); carrito.setId(10L); carrito.setOwner(user);
        mockSecurityContext("agus", false);

        when(usuarioRepository.findByUsername("agus")).thenReturn(Optional.of(user));
        when(carritoRepository.findByOwnerId(1L)).thenReturn(Optional.of(carrito));

        CarritoModel result = carritoService.getCarritoByOwnerId();

        assertEquals(10L, result.getId());
    }

    @Test
    void getCarritoByOwnerId_usuarioNoExiste() {
        mockSecurityContext("nadie", false);
        when(usuarioRepository.findByUsername("nadie")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> carritoService.getCarritoByOwnerId());
    }

    @Test
    void getCarritoByOwnerId_carritoNoExiste() {
        UsuarioModel user = new UsuarioModel(); user.setId(2L); user.setUsername("agus");
        mockSecurityContext("agus", false);

        when(usuarioRepository.findByUsername("agus")).thenReturn(Optional.of(user));
        when(carritoRepository.findByOwnerId(2L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> carritoService.getCarritoByOwnerId());
    }

    @Test
    void save_guardadoCorrectamente() {
        CarritoModel model = new CarritoModel(); model.setId(99L);
        when(carritoRepository.save(model)).thenReturn(model);
        assertEquals(99L, carritoService.save(model).getId());
    }

    @Test
    void deleteCarrito_noExiste() {
        when(carritoRepository.findById(10L)).thenReturn(Optional.empty());
        mockSecurityContext("admin", true);
        assertThrows(BusinessException.class, () -> carritoService.deleteCarrito(10L));
    }

    @Test
    void deleteCarrito_usuarioNoAdmin_lanzaSecurityException() {
        CarritoModel c = new CarritoModel(); c.setId(1L);
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(c));
        mockSecurityContext("agus", false);
        assertThrows(SecurityException.class, () -> carritoService.deleteCarrito(1L));
    }

    @Test
    void deleteCarrito_adminEliminaCarrito() {
        CarritoModel c = new CarritoModel(); c.setId(1L);
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(c));
        mockSecurityContext("admin", true);

        carritoService.deleteCarrito(1L);
        verify(carritoRepository).deleteById(1L);
    }

    @Test
    void deleteProductoCarrito_eliminaProducto() {
        CarritoModel carrito = new CarritoModel();
        carrito.setId(1L);
        carrito.setProductos(new ArrayList<>(List.of(new ItemCarritoModel() {{
            setProductoId(5L);
            setCantidad(1);
            setPrecioUnitario(100);
            setSubTotal(100);
        }})));
        UsuarioModel owner = new UsuarioModel(); owner.setId(99L);
        carrito.setOwner(owner);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(usuarioRepository.findByUsername("agus")).thenReturn(Optional.of(owner));
        mockSecurityContext("agus", false);

        carritoService.deleteProductoCarrito(1L, 5L);

        assertTrue(carrito.getProductos().isEmpty());
    }

    @Test
    void incrementarCantidad_conStockSuficiente() {
        UsuarioModel owner = new UsuarioModel(); owner.setId(1L);
        CarritoModel carrito = new CarritoModel();
        carrito.setOwner(owner);
        carrito.setProductos(new ArrayList<>(List.of(new ItemCarritoModel() {{
            setProductoId(1L); setCantidad(1); setPrecioUnitario(100);
        }})));

        ProductoModel producto = new ProductoModel(); producto.setId(1L); producto.setStock(5); producto.setPrice(100);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(usuarioRepository.findByUsername("agus")).thenReturn(Optional.of(owner));
        when(productoService.findById(1L)).thenReturn(producto);
        when(carritoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        mockSecurityContext("agus", false);

        CarritoModel result = carritoService.incrementarCantidad(1L, 1L);

        assertEquals(2, result.getProductos().get(0).getCantidad());
    }

    @Test
    void agregarProducto_nuevoItemYStockSuficiente() {
        UsuarioModel user = new UsuarioModel(); user.setId(2L); user.setUsername("agus");
        ProductoModel producto = new ProductoModel(); producto.setId(99L); producto.setStock(10); producto.setPrice(100);

        when(usuarioRepository.findByUsername("agus")).thenReturn(Optional.of(user));
        when(carritoRepository.findByOwnerId(2L)).thenReturn(Optional.empty());
        when(productoService.findById(99L)).thenReturn(producto);
        when(carritoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        mockSecurityContext("agus", false);

        CarritoModel result = carritoService.agregarProducto(99L, 3);

        assertEquals(1, result.getProductos().size());
        assertEquals(300, result.getTotal());
    }
}
