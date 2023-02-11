package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurity{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //PERMITE TODAS LAS PETICIONES DE UN USUARIO AUTENTICADO Y LE MANDA AL OAUTH2 RESOURCE SERVER
        //EL TOKEN JWT.
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}
