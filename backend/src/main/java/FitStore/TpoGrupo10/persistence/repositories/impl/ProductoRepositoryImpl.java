package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.ProductoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.persistence.daos.ProductoDao;
import FitStore.TpoGrupo10.persistence.entities.CategoriaEntity;
import FitStore.TpoGrupo10.persistence.entities.ProductoEntity;
import FitStore.TpoGrupo10.persistence.entities.UsuarioEntity;
import FitStore.TpoGrupo10.persistence.mappers.CategoriaMapper;
import FitStore.TpoGrupo10.persistence.mappers.ProductoMapper;
import FitStore.TpoGrupo10.persistence.mappers.UsuarioMapper;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import FitStore.TpoGrupo10.persistence.repositories.ProductoRepository;
import FitStore.TpoGrupo10.persistence.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private final ProductoDao productoDao;
    private final ProductoMapper mapper;
    private final CategoriaMapper categoriaMapper;
    private final UsuarioMapper usuarioMapper;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ProductoRepositoryImpl(ProductoDao productoDao, ProductoMapper mapper, CategoriaMapper categoriaMapper, UsuarioMapper usuarioMapper, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.productoDao = productoDao;
        this.mapper = mapper;
        this.categoriaMapper = categoriaMapper;
        this.usuarioMapper = usuarioMapper;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<ProductoModel> findAll(Pageable pageable) {
        return productoDao.findAll(pageable).map(mapper::toModel);
    }

    @Override
    public ProductoModel update(Long id, ProductoModel model) {

        ProductoEntity entity = productoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        CategoriaEntity categoria = categoriaMapper.toEntity(model.getCategory());
        UsuarioEntity owner = usuarioMapper.toEntity(model.getOwner());

        entity.updateFromModel(model, categoria, owner);

        ProductoEntity actualizado = saveEntity(entity);
        return mapper.toModel(actualizado);
    }

    private ProductoEntity saveEntity(ProductoEntity entity) {
        return productoDao.save(entity);
    }

    @Override
    public ProductoModel findById(Long id) {
        return mapper.toModel(productoDao.findById(id).orElseThrow());
    }

    @Override
    public ProductoModel saveEntity(ProductoModel model) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(model.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        UsuarioModel ownerModel = usuarioRepository.findById(model.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ProductoEntity entity = mapper.toEntity(model);
        UsuarioEntity ownerEntity = usuarioMapper.toEntity(ownerModel);

        entity.setCategory(categoriaEntity);
        entity.setOwner(ownerEntity);

        ProductoEntity savedEntity = productoDao.save(entity);
        return mapper.toModel(savedEntity);
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
