package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.CategoriaModel;
import FitStore.TpoGrupo10.presentation.dto.CategoriaDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T19:02:52-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CategoriaPresentationMapperImpl implements CategoriaPresentationMapper {

    @Override
    public CategoriaModel toModel(CategoriaDto dto) {
        if ( dto == null ) {
            return null;
        }

        CategoriaModel categoriaModel = new CategoriaModel();

        if ( dto.getId() != null ) {
            categoriaModel.setId( Long.parseLong( dto.getId() ) );
        }
        categoriaModel.setNombre( dto.getNombre() );

        return categoriaModel;
    }

    @Override
    public CategoriaDto toDto(CategoriaModel model) {
        if ( model == null ) {
            return null;
        }

        CategoriaDto categoriaDto = new CategoriaDto();

        if ( model.getId() != null ) {
            categoriaDto.setId( String.valueOf( model.getId() ) );
        }
        categoriaDto.setNombre( model.getNombre() );

        return categoriaDto;
    }

    @Override
    public List<CategoriaDto> toDtoList(List<CategoriaModel> models) {
        if ( models == null ) {
            return null;
        }

        List<CategoriaDto> list = new ArrayList<CategoriaDto>( models.size() );
        for ( CategoriaModel categoriaModel : models ) {
            list.add( toDto( categoriaModel ) );
        }

        return list;
    }
}
