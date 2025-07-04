package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import FitStore.TpoGrupo10.business.service.FirebaseStorageService;
import FitStore.TpoGrupo10.business.service.ProductoService;
import FitStore.TpoGrupo10.security.exception.SecurityException;
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
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO.getMessage() + ", id: " + id, ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO));
    }

    @Override
    public ProductoModel save(ProductoModel model, String ownerUsername, MultipartFile[] images) {
        CategoriaModel categoria = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CATEGORIA_NO_ENCONTRADA.getMessage(), ErrorCodeEnum.CATEGORIA_NO_ENCONTRADA));

        UsuarioModel owner = usuarioRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.getMessage(), ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        if (images == null || images.length == 0) {
            throw new BusinessException(ErrorCodeEnum.IMAGENES_OBLIGATORIAS.getMessage(), ErrorCodeEnum.IMAGENES_OBLIGATORIAS);
        }
        if (images.length > 10) {
            throw new BusinessException(ErrorCodeEnum.IMAGENES_EXCEDIDAS.getMessage(), ErrorCodeEnum.IMAGENES_EXCEDIDAS);
        }
        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) {
                throw new BusinessException(ErrorCodeEnum.IMAGEN_VACIA.getMessage(), ErrorCodeEnum.IMAGEN_VACIA);
            }
        }

        List<String> imageUrls = subirImagenes(images);
        model.setImages(imageUrls);
        model.setCategory(categoria);
        model.setOwner(owner);

        return productoRepository.save(model);
    }

    @Override
    public ProductoModel update(Long id, ProductoModel model, MultipartFile[] newImages, List<String> existingImageUrls) {
        ProductoModel existente = findById(id);
        validarPropietario(existente);

        CategoriaModel categoria = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CATEGORIA_NO_ENCONTRADA.getMessage(), ErrorCodeEnum.CATEGORIA_NO_ENCONTRADA));

        List<String> finalImages = new ArrayList<>();

        // Agregar imágenes existentes que se quieren conservar
        if (existingImageUrls != null) {
            finalImages.addAll(existingImageUrls);
        }

        // Subir nuevas imágenes si se mandaron
        if (newImages != null && newImages.length > 0) {
            if (newImages.length + finalImages.size() > 10) {
                throw new BusinessException(ErrorCodeEnum.IMAGENES_EXCEDIDAS.getMessage(), ErrorCodeEnum.IMAGENES_EXCEDIDAS);
            }

            for (MultipartFile image : newImages) {
                if (image == null || image.isEmpty()) {
                    throw new BusinessException(ErrorCodeEnum.IMAGEN_VACIA.getMessage(), ErrorCodeEnum.IMAGEN_VACIA);
                }
            }

            finalImages.addAll(subirImagenes(newImages));
        }

        // Limpiar antes de setear para reordenar las imagenes
        existente.getImages().clear();
        productoRepository.save(existente); // fuerza el delete previo para evitar duplicados

        // Ahora sí setear el resto
        model.setImages(finalImages);
        model.setCategory(categoria);
        model.setOwner(existente.getOwner());
        existente.updateFrom(model);

        return productoRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new BusinessException(ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO.getMessage() + ", id: " + id, ErrorCodeEnum.PRODUCTO_NO_ENCONTRADO);
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
            throw new BusinessException(ErrorCodeEnum.IMAGENES_OBLIGATORIAS.getMessage(), ErrorCodeEnum.IMAGENES_OBLIGATORIAS);
        }

        int cantidadActual = existente.getImages().size();
        if (cantidadActual + images.length > 10) {
            throw new BusinessException(ErrorCodeEnum.IMAGENES_EXCEDIDAS.getMessage(), ErrorCodeEnum.IMAGENES_EXCEDIDAS);
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
                throw new BusinessException(ErrorCodeEnum.ERROR_SUBIDA_ARCHIVO.getMessage() + ": " + file.getOriginalFilename(), ErrorCodeEnum.ERROR_SUBIDA_ARCHIVO, e);
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
            throw new SecurityException(ErrorCodeEnum.ACCESS_DENIED.getMessage(), ErrorCodeEnum.ACCESS_DENIED);
        }
    }
}