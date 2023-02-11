package ve.com.tps.gatewayservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    //SECURITY CONFIG HACIENDO USO DE WEB FLUX SECURITY

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){

        //AUTORIZA INTERCAMBIOS QUE ESTÉN AUTENTICADOS, Y LOGEADOS CON OAUTH2
        http
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Login(Customizer.withDefaults());
        //DESACTIVAMOS CSRF
        http
                .csrf()
                .disable();
        return http.build();



    }
}
