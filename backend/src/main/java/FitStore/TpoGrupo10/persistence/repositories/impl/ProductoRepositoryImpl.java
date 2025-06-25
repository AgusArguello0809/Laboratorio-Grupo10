package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.persistence.daos.ProductoDao;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.mappers.ProductoPersistenceMapper;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoRepositoryImpl.class);
    private final ProductoDao productoDao;
    private final ProductoPersistenceMapper mapper;

    public ProductoRepositoryImpl(ProductoDao productoDao, ProductoPersistenceMapper mapper) {
        this.productoDao = productoDao;
        this.mapper = mapper;
    }

    @Override
    public Page<ProductoModel> findAll(Predicate predicate, Pageable pageable) {
        LOGGER.debug("Buscando todos los productos de la página {} con tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        return productoDao.findAll(predicate, pageable).map(mapper::toModel);
    }

    @Override
    public Optional<ProductoModel> findById(Long id) {
        LOGGER.debug("Buscando producto con id: {}", id);
        return productoDao.findById(id).map(mapper::toModel);
    }

    @Override
    @Transactional
    public ProductoModel save(ProductoModel model) {
        LOGGER.debug("Guardando producto: {}", model);
        ProductoEntity entity = mapper.toEntity(model);
        ProductoEntity saved = productoDao.save(entity);
        return mapper.toModel(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        LOGGER.debug("Eliminando producto con id: {}", id);
        productoDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LOGGER.debug("Comprobando existencia de producto con id: {}", id);
        return productoDao.existsById(id);
    }
}
