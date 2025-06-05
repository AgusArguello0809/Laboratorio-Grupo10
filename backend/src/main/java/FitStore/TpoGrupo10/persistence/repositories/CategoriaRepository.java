package FitStore.TpoGrupo10.persistence.repositories;

import FitStore.TpoGrupo10.models.CategoriaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepository {
    Page<CategoriaModel> findAll(Pageable pageable);
}
