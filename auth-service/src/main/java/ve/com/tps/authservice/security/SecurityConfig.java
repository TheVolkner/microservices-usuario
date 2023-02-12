package ve.com.tps.authservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //DESACTIVAMOS EL CSRF Y AUTORIZAMOS TODAS LAS PETICIONES AL SERVICIO
        http.
                csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();

        return http.build();
    }
}
