package FitStore.TpoGrupo10.service.impl;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import FitStore.TpoGrupo10.service.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Page<ProductoModel> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public ProductoModel findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public ProductoModel create(ProductoModel producto) {
        return productoRepository.saveEntity(producto);
    }

    @Override
    public ProductoModel update(Long id, ProductoModel producto) {
        return productoRepository.update(id, producto);
    }

    @Override
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }
}
