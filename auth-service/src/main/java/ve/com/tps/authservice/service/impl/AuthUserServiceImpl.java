package ve.com.tps.authservice.service.impl;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ve.com.tps.authservice.dto.AuthUserDTO;
import ve.com.tps.authservice.dto.TokenDTO;
import ve.com.tps.authservice.entity.AuthUser;
import ve.com.tps.authservice.repository.AuthUserRepository;
import ve.com.tps.authservice.security.JwtProvider;
import ve.com.tps.authservice.service.AuthUserService;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    //INYECTAMOS LAS DEPENDENCIAS DEL REPOSITORIO
    //EL PASSWORD ENCODER Y EL TOKEN PROVIDER
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;


    //GUARDAMOS UN USUARIO EN LA BBDD
    @Override
    public AuthUser save(AuthUserDTO dto) {

        //COMPROBAMOS QUE NO  EXISTE UN USUARIO YA CON EL USERNAME INDICADO
        Optional<AuthUser> authUserOptional = authUserRepository.findByUsername(dto.getUsername());

        //SI NO HAY UN USUARIO CON ESE USERNAME, PROCEDEMOS
        if (authUserOptional.isEmpty()) {

            String password = passwordEncoder.encode(dto.getPassword());
            AuthUser user = AuthUser
                    .builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .build();
            return authUserRepository.save(user);
        } else {

            return null;
        }
    }

    //LOGIN Y SOLICITUD DE TOKEN JWT
    @Override
    public TokenDTO login(AuthUserDTO dto) {

        //COMPROBAMOS QUE EL USUARIO EXISTE
        Optional<AuthUser> authUserOptional = authUserRepository.findByUsername(dto.getUsername());

        //SI EXISTE UN USUARIO CON ESE USERNAME, PROCEDEMOS
        if (authUserOptional.isPresent()) {

            //COMPROBAMOS QUE LA CONTRASEÑA ENVIADA POR EL CLIENTE COINCIDE CON LA GUARDADA EN LA ENTIDAD
            if (passwordEncoder.matches(dto.getPassword(), authUserOptional.get().getPassword())) {

                //CREAMOS LA SESIÓN JWT
                return new TokenDTO(jwtProvider.createToken(authUserOptional.get()));

            } else {
                return null;
            }

        } else {

            return null;
        }

    }


    //VALIDAMOS EL TOKEN
    @Override
    public boolean validate(TokenDTO token) {

        //COMPROBAMOS SI EL TOKEN JWT ES VÁLIDO EN LA SESIÓN
        if(jwtProvider.validate(token.getToken())){

           String username = jwtProvider.getUsernameFromToken(token.getToken());

           //COMPROBAMOS QUE EL USUARIO EL CÚAL TIENE EL TOKEN ASIGNADO EXISTE
           if(authUserRepository.findByUsername(username).isPresent()){
               return true;
           } else {
               return false;
           }

        } else {

            return false;
        }
    }
}
