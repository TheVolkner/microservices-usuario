package ve.com.tps.usuarioservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//REST TEMPLATE ES UN PATRÓN CON EL QUE PODREMOS COMUNICAR MICROSERVICIOS
@Configuration
public class RestTemplateConfig {

    //CREAMOS UN BEAN PARA OBTENER EL REST TEMPLATE
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
