package ve.com.tps.authservice.service;

import ve.com.tps.authservice.dto.AuthUserDTO;
import ve.com.tps.authservice.dto.TokenDTO;
import ve.com.tps.authservice.entity.AuthUser;

public interface AuthUserService {

    AuthUser save(AuthUserDTO dto);

    TokenDTO login(AuthUserDTO dto);

    boolean validate(TokenDTO dto);

}
