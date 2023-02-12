package ve.com.tps.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//UN DTO PARA ENVIAR CADA PETICIÓN AL GATEWAY INDICANDO EL TIPO DE PETICION Y LA RUTA,PROVENIENTE DEL GATEWAY
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDTO {

    private String uri;

    private String method;
}
