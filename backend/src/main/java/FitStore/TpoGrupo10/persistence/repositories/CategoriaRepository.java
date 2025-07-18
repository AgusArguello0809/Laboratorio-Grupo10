package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.CategoriaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoriaRepository {
    Page<CategoriaModel> findAll(Pageable pageable);

    Optional<CategoriaModel> findById(Long id);

    long count();

    void saveAll(Iterable<CategoriaModel> categorias);
}
