package FitStore.TPO_Grupo10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FitStore.TPO_Grupo10.model.Producto;
import FitStore.TPO_Grupo10.repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service //Un servcio es una clase que contiene la logica de negocio osea que es una clase que contiene metodos que realizan operaciones sobre los datos
@Transactional //Indica que los metodos de esta clase son transaccionales osea que se ejecutan en una transaccion de base de datos
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired 
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository; //Inyecta el repositorio de productos
    }
    //CRUD

    public List<Producto> getAllProductos() {
        return productoRepository.findAll(); //Devuelve todos los productos
    }
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id)); //Devuelve un producto por su ID
    }
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto); //Guarda un nuevo producto
    }
    public Producto updateProducto(Long id, Producto producto) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        producto.setId(id); //Asegura que el ID del producto a actualizar sea el correcto
        return productoRepository.save(producto); //Actualiza un producto existente
    }
    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id); //Elimina un producto por su ID
    }

}
