package FitStore.TpoGrupo10.business.service;

import FitStore.TpoGrupo10.models.OrdenModel;

import java.util.List;

public interface OrdenService {
    OrdenModel save(OrdenModel orden);
    OrdenModel findById(Long id);
    List<OrdenModel> findByCompradorId(Long compradorId);
}
