package ve.com.tps.authservice.controller;

import antlr.Token;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.authservice.dto.AuthUserDTO;
import ve.com.tps.authservice.dto.TokenDTO;
import ve.com.tps.authservice.entity.AuthUser;
import ve.com.tps.authservice.service.AuthUserService;

@RestController
@RequestMapping("api/auth")
public class AuthServiceController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDTO authUserDTO){

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
    public ResponseEntity<TokenDTO> validate(@PathVariable String token){

       if(authUserService.validate(token)){

           return new ResponseEntity<>(new TokenDTO(token),HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
