package ve.com.tps.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO PARA MANEJAR EL VALOR DEL TOKEN
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {

    private String token;

}
