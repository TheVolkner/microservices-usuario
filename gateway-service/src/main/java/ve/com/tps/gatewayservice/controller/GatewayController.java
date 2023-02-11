package ve.com.tps.gatewayservice.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    //ESTOS MÉTODOS TRABAJAN CON SPRING WEB FLUX, PROGRAMACIÓN REACTIVA.

    //OBTENEMOS EL ID DE LA SESIÓN ACTIVA
    @GetMapping("/")
    public Mono<String> index(WebSession session){

        return Mono.just(session.getId());
    }

    //OBTENEMOS EL TOKEN DE LA SESIÓN ACTIVA
    @GetMapping("/token")
    public Mono<String> getToken(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client){

        return Mono.just(client.getAccessToken().getTokenValue());
    }
}
