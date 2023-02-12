package ve.com.tps.authservice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ve.com.tps.authservice.dto.RequestDTO;

import java.util.List;
import java.util.regex.Pattern;

//HACEMOS UN VALIDADOR PARA LAS RUTAS HTTP QUE SE ENVIEN AL GATEWAY
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {

    //CREAMOS UNA LISTA CON LOS PATHS
    private List<RequestDTO> paths;

    //EN ESTE MÉTODO COMPROBAMOS QUE LA PETICIÓN COINCIDA CON UNA DE LAS PETICIONES DE LA LISTA
    public boolean isAdminPath(RequestDTO dto){

        return paths.stream().anyMatch(p ->
                Pattern.matches(p.getUri(),dto.getUri()) && p.getMethod().equals(dto.getMethod()));
    }
}
