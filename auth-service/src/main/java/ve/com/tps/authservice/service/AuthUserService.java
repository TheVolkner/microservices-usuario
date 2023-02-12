package ve.com.tps.authservice.service;

import ve.com.tps.authservice.dto.AuthUserDTO;
import ve.com.tps.authservice.dto.NewAuthUserDTO;
import ve.com.tps.authservice.dto.RequestDTO;
import ve.com.tps.authservice.dto.TokenDTO;
import ve.com.tps.authservice.entity.AuthUser;

public interface AuthUserService {

    AuthUser save(NewAuthUserDTO dto);

    TokenDTO login(AuthUserDTO dto);

    boolean validate(String token, RequestDTO requestDTO);

}
