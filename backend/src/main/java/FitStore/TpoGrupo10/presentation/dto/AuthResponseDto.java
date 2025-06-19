package FitStore.TpoGrupo10.presentation.dto;

public class AuthResponseDto {
    private final String jwt;

    public AuthResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
