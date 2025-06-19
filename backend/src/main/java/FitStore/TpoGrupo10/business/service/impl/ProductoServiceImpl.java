package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCode;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.business.service.FirebaseStorageService;
import FitStore.TpoGrupo10.business.service.ProductoService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
                .orElseThrow(() -> new BusinessException("Producto no encontrado con id: " + id, ErrorCode.PRODUCTO_NO_ENCONTRADO));
    }

    @Override
    public ProductoModel save(ProductoModel model, String ownerUsername, MultipartFile[] images) {
        CategoriaModel categoria = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new BusinessException("Categoría no encontrada", ErrorCode.CATEGORIA_NO_ENCONTRADA));

        UsuarioModel owner = usuarioRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", ErrorCode.USUARIO_NO_ENCONTRADO));

        if (images == null || images.length == 0) {
            throw new BusinessException("Debe subir al menos una imagen.", ErrorCode.IMAGENES_OBLIGATORIAS);
        }
        if (images.length > 10) {
            throw new BusinessException("No se pueden subir más de 10 imágenes.", ErrorCode.IMAGENES_EXCEDIDAS);
        }
        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) {
                throw new BusinessException("No se permiten imágenes vacías.", ErrorCode.IMAGEN_VACIA);
            }
        }

        List<String> imageUrls = subirImagenes(images);
        model.setImages(imageUrls);
        model.setCategory(categoria);
        model.setOwner(owner);

        return productoRepository.save(model);
    }

    @Override
    public ProductoModel update(Long id, ProductoModel model, MultipartFile[] images) {
        ProductoModel existente = findById(id);
        validarPropietario(existente);

        CategoriaModel categoria = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new BusinessException("Categoria no encontrada", ErrorCode.CATEGORIA_NO_ENCONTRADA));

        if (images != null) {
            if (images.length > 10) {
                throw new BusinessException("No se pueden subir más de 10 imagenes.", ErrorCode.IMAGENES_EXCEDIDAS);
            }
            if (images.length > 0) {
                model.setImages(subirImagenes(images));
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
            throw new BusinessException("Producto no encontrado con id: " + id, ErrorCode.PRODUCTO_NO_ENCONTRADO);
        } else {
            validarPropietario(findById(id));
        }
        productoRepository.deleteById(id);
    }

    @Override
    public ProductoModel agregarImagenes(Long id, MultipartFile[] images) {
        ProductoModel existente = findById(id);
        validarPropietario(existente);
        if (images == null || images.length == 0) {
            throw new BusinessException("Debe subir al menos una imagen.", ErrorCode.IMAGENES_OBLIGATORIAS);
        }

        int cantidadActual = existente.getImages().size();
        if (cantidadActual + images.length > 10) {
            throw new BusinessException("No se pueden superar las 10 imagenes.", ErrorCode.IMAGENES_EXCEDIDAS);
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
        validarPropietario(existente);
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

    private List<String> subirImagenes(MultipartFile[] images) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : images) {
            try {
                urls.add(storageService.uploadFile(file));
            } catch (Exception e) {
                throw new BusinessException("Error al subir imagen: " + file.getOriginalFilename(), ErrorCode.ERROR_SUBIDA_ARCHIVO);
            }
        }
        return urls;
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private void validarPropietario(ProductoModel producto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!producto.getOwner().getUsername().equals(username) && !isAdmin()) {
            throw new BusinessException("No tiene permisos sobre este producto.", ErrorCode.ACCESS_DENIED);
        }
    }
}
