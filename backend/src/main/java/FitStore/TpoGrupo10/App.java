package FitStore.TpoGrupo10;

import FitStore.TpoGrupo10.models.CarritoModel;
import FitStore.TpoGrupo10.models.UsuarioModel;
import FitStore.TpoGrupo10.service.CarritoService;
import FitStore.TpoGrupo10.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner init(UsuarioService usuarioService, CarritoService carritoService) {
        return args -> {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setUsername("fituser");
            usuario.setName("Agus");
            usuario.setLastName("Argüello");
            usuario.setEmail("agus@example.com");
            usuario.setPassword("123456");

            UsuarioModel savedUsuario = usuarioService.save(usuario);

            CarritoModel carrito = new CarritoModel();
            carrito.setOwner(savedUsuario);

            carritoService.save(carrito);

            System.out.println("✅ Usuario y Carrito de prueba creados");
        };
    }

}