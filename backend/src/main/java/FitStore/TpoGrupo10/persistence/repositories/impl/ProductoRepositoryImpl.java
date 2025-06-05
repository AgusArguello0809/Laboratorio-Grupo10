package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.persistence.daos.ProductoDao;
import FitStore.TpoGrupo10.persistence.mappers.ProductoMapper;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private final ProductoDao productoDao;
    private final ProductoMapper mapper;

    public ProductoRepositoryImpl(ProductoDao productoDao, ProductoMapper mapper) {
        this.productoDao = productoDao;
        this.mapper = mapper;
    }

    @Override
    public Page<ProductoModel> findAll(Pageable pageable) {
        return productoDao.findAll(pageable).map(mapper::toModel);
    }

    @Override
    public ProductoModel findById(Long id) {
        return mapper.toModel(productoDao.findById(id).orElseThrow());
    }

    @Override
    public ProductoModel save(ProductoModel model) {
        return mapper.toModel(productoDao.save(mapper.toEntity(model)));
    }

    @Override
    public void deleteById(Long id) {
        productoDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productoDao.existsById(id);
    }
}
