package FitStore.TpoGrupo10.presentation.mappers;
import FitStore.TpoGrupo10.models.OrdenModel;
import FitStore.TpoGrupo10.presentation.dto.response.OrdenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductoPresentationMapper.class, ItemOrdenPresentationMapper.class})
public interface OrdenPresentationMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "comprador.id", target = "compradorId")
    @Mapping(source = "productos", target = "productos")
    OrdenDto toDto(OrdenModel model);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "compradorId", target = "comprador.id")
    @Mapping(source = "productos", target = "productos")
    OrdenModel toModel(OrdenDto dto);
}
