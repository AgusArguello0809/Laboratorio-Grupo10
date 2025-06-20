package FitStore.TpoGrupo10.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {

    @NotNull(message = "El usuario no puede ser nulo")
    @NotBlank(message = "El usuario no puede ser vacio")
    @Size(min = 8, max = 32,message = "El usuario debe tener entre 8 y 32 caracteres")
    private String username;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede ser vacio")
    private String name;

    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede ser vacio")
    private String lastName;

    @NotNull(message = "El email no puede ser nulo")
    @NotBlank(message = "El email no puede estar vacio")
    @Email
    private String email;

    @NotNull(message = "La password no puede ser nula")
    @NotBlank(message = "La password no puede ser vacia")
    @Size(min = 8, max = 32, message = "La password debe tener entre 8 y 32 caracteres")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}