package FitStore.TpoGrupo10.business.service;

import FitStore.TpoGrupo10.models.CategoriaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {
    Page<CategoriaModel> findAll(Pageable pageable);
}
