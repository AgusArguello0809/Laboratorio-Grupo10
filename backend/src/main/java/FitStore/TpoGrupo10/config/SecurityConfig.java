package FitStore.TpoGrupo10.config;

import FitStore.TpoGrupo10.security.exception.CustomAccessDeniedHandler;
import FitStore.TpoGrupo10.security.exception.CustomAuthenticationEntryPoint;
import FitStore.TpoGrupo10.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TODO: Tests unitarios
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomAccessDeniedHandler accessDeniedHandler, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // login y registro

                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Swagger

                        .requestMatchers(HttpMethod.GET, "/productos/**", "/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/productos/**").hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/productos/**").hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/productos/**").hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAnyRole("CLIENTE", "ADMIN")


                        .requestMatchers("/carritos/**").hasAnyRole("CLIENTE", "ADMIN")

                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        .anyRequest().permitAll()
                );


        http.exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
        ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
