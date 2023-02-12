package ve.com.tps.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ve.com.tps.authservice.entity.AuthUser;
import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {


  @Value("${jwt.secret}")
   private String secret;

  //CODIFICAMOS LA PALABRA SECRETA LUEGO DE INICIALIZAR LAS DEPENDENCIAS
  @PostConstruct
  protected void init(){

      secret = Base64.getEncoder().encodeToString(secret.getBytes());

  }

  //GENERAMOS EL TOKEN JWT
  public String createToken(AuthUser user){

      //CREAMOS EL MAPA CON LOS CLAIMS
     Map<String,Object> claims = new HashMap<>();

     //GUARDAMOS EL NOMBRE,EL ID, Y LAS CREDENCIALES DE FECHA DEL TOKEN
     claims = Jwts.claims().setSubject(user.getUsername());
     claims.put("id",user.getId());
     Date now = new Date();
     Date exp = new Date(now.getTime() + 3600000);
     //GENERAMOS EL TOKEN CON SUS CLAIMS, LA FECHA Y LA FIRMA
     return Jwts.builder()
             .setClaims(claims)
             .setIssuedAt(now)
             .setExpiration(exp)
             .signWith(SignatureAlgorithm.HS256,secret)
             .compact();
  }

  //VALIDAMOS QUE EL TOKEN SEA VÁLIDO
  public boolean validate(String token){
      try {

          Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
          return true;

      } catch(Exception e){

          return false;
      }
  }

  //Obtenemos el Subject del Token, el cúal es el nombre de Usuario.
  public String getUsernameFromToken(String token){

      try {

          return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

      } catch(Exception e){

         return "Bad Token";
      }
  }
}
