package FitStore.TpoGrupo10.persistence.repositories.impl;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.persistence.daos.CategoriaDao;
import FitStore.TpoGrupo10.persistence.mappers.CategoriaMapper;
import FitStore.TpoGrupo10.persistence.repositories.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRepositoryImpl.class);
    private final CategoriaDao categoriaDao;
    private final CategoriaMapper mapper;

    public CategoriaRepositoryImpl(CategoriaDao categoriaDao, CategoriaMapper mapper) {
        this.categoriaDao = categoriaDao;
        this.mapper = mapper;
    }

    @Override
    public Page<CategoriaModel> findAll(Pageable pageable) {
        LOGGER.debug("Buscando todas las categorias de la página {} con tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        return categoriaDao
                .findAll(pageable)
                .map(mapper::toModel);
    }
}
