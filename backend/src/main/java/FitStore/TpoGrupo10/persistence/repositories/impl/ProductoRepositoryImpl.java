package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.persistence.daos.ProductoDao;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.mappers.ProductoMapper;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoRepositoryImpl.class);
    private final ProductoDao productoDao;
    private final ProductoMapper mapper;

    public ProductoRepositoryImpl(ProductoDao productoDao, ProductoMapper mapper) {
        this.productoDao = productoDao;
        this.mapper = mapper;
    }

    @Override
    public Page<ProductoModel> findAll(Predicate predicate, Pageable pageable) {
        try {
            LOGGER.debug("Buscando todos los productos de la página {} con tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
            return productoDao.findAll(predicate, pageable).map(mapper::toModel);
        } catch (DataAccessException e) {
            LOGGER.error("Error buscando productos", e);
            throw new RuntimeException("Error buscando productos", e);
        }
    }

    @Override
    public Optional<ProductoModel> findById(Long id) {
        try {
            return productoDao.findById(id).map(mapper::toModel);
        } catch (DataAccessException e) {
            LOGGER.error("Error buscando producto con id {}", id, e);
            throw new RuntimeException("Error buscando producto", e);
        }
    }

    @Override
    @Transactional
    public ProductoModel save(ProductoModel model) {
        try {
            ProductoEntity entity = mapper.toEntity(model);
            ProductoEntity saved = productoDao.save(entity);
            return mapper.toModel(saved);
        } catch (DataAccessException e) {
            LOGGER.error("Error guardando producto", e);
            throw new RuntimeException("Error guardando producto", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            productoDao.deleteById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Error eliminando producto con id {}", id, e);
            throw new RuntimeException("Error eliminando producto", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return productoDao.existsById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Error verificando existencia de producto con id {}", id, e);
            throw new RuntimeException("Error verificando existencia de producto", e);
        }
    }
}
