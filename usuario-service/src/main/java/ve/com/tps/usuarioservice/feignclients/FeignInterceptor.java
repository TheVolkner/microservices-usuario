package ve.com.tps.usuarioservice.feignclients;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

//AL SER ESTE EL SERVICIO PRINCIPAL EL CUAL LLAMA A LOS OTROS SERVICIOS,DEBE ENVIAR EL TOKEN AUTORIZADO PARA
//SOLICITAR DATOS A LOS SERVICIOS ESPECIFICADOS,SE OBTIENE EL TOKEN OBTENIDO DEL CONTEXTO DE SEGURIDAD
//Y SE ENVÍA JUNTO CON LA PETICIÓN COMO UN HEADER
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestTemplate.header("Authorization", "Bearer " + jwt.getTokenValue());
    }
}
