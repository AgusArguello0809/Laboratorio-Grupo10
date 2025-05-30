package FitStore.TPO_Grupo10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import FitStore.TPO_Grupo10.model.Producto;
import FitStore.TPO_Grupo10.repository.ProductoRepository;
import FitStore.TPO_Grupo10.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public List<Producto> getAllProductos() {
        try {
            return productoService.getAllProductos();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los productos: " + e.getMessage());
        }
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable Long id) {
        try {
            Producto producto = productoService.getProductoById(id);
            if (producto != null) {
                return producto;
            } else {
                throw new RuntimeException("Producto no encontrado con id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el producto: " + e.getMessage());
        }
    }

    // Crear un nuevo producto
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        try {
            return productoService.createProducto(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }
    
    // Actualizar un producto existente
    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            return productoService.updateProducto(id, producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el producto: " + e.getMessage());
        }
    }   

    //Eliminar un producto  
    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        try {
            productoService.deleteProducto(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
