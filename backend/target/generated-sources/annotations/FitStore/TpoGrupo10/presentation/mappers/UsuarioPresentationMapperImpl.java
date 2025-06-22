package FitStore.TpoGrupo10.presentation.mappers;

import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.presentation.dto.UsuarioCreateDto;
import FitStore.TpoGrupo10.presentation.dto.response.UsuarioResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-22T13:01:50-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UsuarioPresentationMapperImpl implements UsuarioPresentationMapper {

    @Override
    public UsuarioModel toModel(UsuarioCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setUsername( dto.getUsername() );
        usuarioModel.setName( dto.getName() );
        usuarioModel.setLastName( dto.getLastName() );
        usuarioModel.setEmail( dto.getEmail() );
        usuarioModel.setPassword( dto.getPassword() );

        return usuarioModel;
    }

    @Override
    public UsuarioResponseDto toResponseDto(UsuarioModel model) {
        if ( model == null ) {
            return null;
        }

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();

        usuarioResponseDto.setId( model.getId() );
        usuarioResponseDto.setUsername( model.getUsername() );
        usuarioResponseDto.setName( model.getName() );
        usuarioResponseDto.setLastName( model.getLastName() );
        usuarioResponseDto.setEmail( model.getEmail() );

        return usuarioResponseDto;
    }

    @Override
    public List<UsuarioResponseDto> toResponseDtoList(List<UsuarioModel> models) {
        if ( models == null ) {
            return null;
        }

        List<UsuarioResponseDto> list = new ArrayList<UsuarioResponseDto>( models.size() );
        for ( UsuarioModel usuarioModel : models ) {
            list.add( toResponseDto( usuarioModel ) );
        }

        return list;
    }

    @Override
    public List<UsuarioModel> toModelList(List<UsuarioCreateDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<UsuarioModel> list = new ArrayList<UsuarioModel>( dtos.size() );
        for ( UsuarioCreateDto usuarioCreateDto : dtos ) {
            list.add( toModel( usuarioCreateDto ) );
        }

        return list;
    }
}
