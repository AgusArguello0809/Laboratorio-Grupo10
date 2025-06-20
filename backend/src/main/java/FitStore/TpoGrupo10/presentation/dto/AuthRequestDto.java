package FitStore.TpoGrupo10.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequestDto {
    @NotNull(message = "El usuario no puede ser nulo")
    @NotBlank(message = "El usuario no puede estar vacio")
    private String username;
    @NotNull(message = "La password no puede ser nula")
    @NotBlank(message = "La password no puede estar vacia")
    private String password;

}
