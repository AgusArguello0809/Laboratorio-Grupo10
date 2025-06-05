package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.UsuarioDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T19:03:13-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UsuarioPresentationMapperImpl implements UsuarioPresentationMapper {

    @Override
    public UsuarioModel toModel(UsuarioDto dto) {
        if ( dto == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId( dto.getId() );
        usuarioModel.setUsername( dto.getUsername() );
        usuarioModel.setName( dto.getName() );
        usuarioModel.setLastName( dto.getLastName() );
        usuarioModel.setEmail( dto.getEmail() );
        usuarioModel.setPassword( dto.getPassword() );

        return usuarioModel;
    }

    @Override
    public UsuarioDto toDto(UsuarioModel model) {
        if ( model == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId( model.getId() );
        usuarioDto.setUsername( model.getUsername() );
        usuarioDto.setName( model.getName() );
        usuarioDto.setLastName( model.getLastName() );
        usuarioDto.setEmail( model.getEmail() );
        usuarioDto.setPassword( model.getPassword() );

        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> toDtoList(List<UsuarioModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<UsuarioDto> list = new ArrayList<UsuarioDto>( modelList.size() );
        for ( UsuarioModel usuarioModel : modelList ) {
            list.add( toDto( usuarioModel ) );
        }

        return list;
    }
}
