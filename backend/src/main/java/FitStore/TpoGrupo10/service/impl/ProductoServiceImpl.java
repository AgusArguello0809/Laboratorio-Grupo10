package FitStore.TpoGrupo10.service.impl;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.service.FirebaseStorageService;
import FitStore.TpoGrupo10.service.ProductoService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FirebaseStorageService storageService;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository,
                               UsuarioRepository usuarioRepository,
                               FirebaseStorageService storageService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.storageService = storageService;
    }

    @Override
    public Page<ProductoModel> findAll(Predicate predicate, Pageable pageable) {
        return productoRepository.findAll(predicate, pageable);
    }

    @Override
    public ProductoModel findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    @Override
    public ProductoModel save(ProductoModel model, MultipartFile[] images) throws IOException {

        // Validar existencia de la categoría
        CategoriaModel categoria = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Validamr existencia del usuario
        UsuarioModel owner = usuarioRepository.findById(model.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Subir las imágenes
        if (images == null || images.length == 0) {
            throw new RuntimeException("Debe subir al menos una imagen.");
        }
        if (images.length > 10) {
            throw new RuntimeException("No se pueden subir más de 10 imágenes.");
        }
        List<String> imageUrls = subirImagenes(images);
        model.setImages(imageUrls);

        // Asignar las entidades correctas
        model.setCategory(categoria);
        model.setOwner(owner);

        return productoRepository.save(model);
    }

    @Override
    public ProductoModel update(Long id, ProductoModel model, MultipartFile[] images) throws IOException {
        ProductoModel existente = findById(id);

        // Validación de categoría
        CategoriaModel categoria = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (images != null) {
            if (images.length > 10) {
                throw new RuntimeException("No se pueden subir más de 10 imágenes.");
            }
            if (images.length > 0) {
                List<String> imageUrls = subirImagenes(images);
                model.setImages(imageUrls);
            } else {
                model.setImages(existente.getImages());
            }
        } else {
            model.setImages(existente.getImages());
        }

        model.setCategory(categoria);
        model.setOwner(existente.getOwner());
        existente.updateFrom(model);

        return productoRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public ProductoModel agregarImagenes(Long id, MultipartFile[] images) throws IOException {
        ProductoModel existente = findById(id);

        if (images == null || images.length == 0) {
            throw new RuntimeException("Debe subir al menos una imagen.");
        }

        int cantidadActual = existente.getImages().size();
        if (cantidadActual + images.length > 10) {
            throw new RuntimeException("No se pueden superar las 10 imágenes.");
        }

        List<String> nuevasImagenes = subirImagenes(images);
        List<String> imagenesActuales = new ArrayList<>(existente.getImages());
        imagenesActuales.addAll(nuevasImagenes);
        existente.setImages(imagenesActuales);

        return productoRepository.save(existente);
    }

    @Override
    public ProductoModel eliminarImagenes(Long id, List<String> imagesToRemove) {
        ProductoModel existente = findById(id);

        List<String> imagenesActuales = new ArrayList<>(existente.getImages());
        imagenesActuales.removeAll(imagesToRemove);
        existente.setImages(imagenesActuales);

        return productoRepository.save(existente);
    }

    @Override
    public ProductoModel actualizarPrecioYStock(Long id, double nuevoPrecio, int nuevoStock) {
        ProductoModel existente = findById(id);
        existente.setPrice(nuevoPrecio);
        existente.setStock(nuevoStock);
        return productoRepository.save(existente);
    }

    private List<String> subirImagenes(MultipartFile[] images) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : images) {
            urls.add(storageService.uploadFile(file));
        }
        return urls;
    }
}
