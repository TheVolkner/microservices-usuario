package ve.com.tps.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO PARA MANEJAR EL USUARIO EN SERVICIO Y CONTROLADOR
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDTO {

    private String username;
    private String password;
}
