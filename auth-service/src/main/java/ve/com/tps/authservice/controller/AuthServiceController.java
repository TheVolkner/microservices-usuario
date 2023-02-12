package ve.com.tps.authservice.controller;

import antlr.Token;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.authservice.dto.AuthUserDTO;
import ve.com.tps.authservice.dto.NewAuthUserDTO;
import ve.com.tps.authservice.dto.RequestDTO;
import ve.com.tps.authservice.dto.TokenDTO;
import ve.com.tps.authservice.entity.AuthUser;
import ve.com.tps.authservice.service.AuthUserService;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthServiceController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewAuthUserDTO authUserDTO){

        AuthUser user = authUserService.save(authUserDTO);

        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthUserDTO authUserDTO){

         TokenDTO tokenDTO = authUserService.login(authUserDTO);

         if(tokenDTO != null){

             return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
         } else {

             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }

    @PostMapping("/validate/{token}")
    public ResponseEntity<TokenDTO> validate(@PathVariable String token, RequestDTO requestDTO){

        log.info("Token: " + token);

        log.info("RequestDTO: " + requestDTO.getUri() + ", " + requestDTO.getMethod());

       if(authUserService.validate(token,requestDTO)){

           return new ResponseEntity<>(new TokenDTO(token),HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
