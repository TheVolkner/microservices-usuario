package ve.com.tps.usuarioservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.usuarioservice.entities.Usuario;
import ve.com.tps.usuarioservice.models.Carro;
import ve.com.tps.usuarioservice.models.Moto;
import ve.com.tps.usuarioservice.services.UsuarioService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;


    @GetMapping()
    public ResponseEntity<List<Usuario>> listarUsuarios(){

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if(!usuarios.isEmpty() && usuarios != null){

            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id){

        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if(usuario != null){

            return new ResponseEntity<>(usuario,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario u){

        Usuario uSaved = usuarioService.agregarUsuario(u);

        if(uSaved != null){

            return new ResponseEntity<>(uSaved,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/modificar")
    public ResponseEntity<Usuario> modificarUsuario(@PathVariable Integer id, @RequestBody Usuario u){

        Usuario uFound = usuarioService.buscarUsuarioPorId(id);

        if(uFound != null){

            u.setId(id);
            Usuario uSaved = usuarioService.modificarUsuario(u);

            if(uSaved != null){

                return new ResponseEntity<>(uSaved,HttpStatus.OK);
            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}/borrar")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Integer id){

        Usuario uFound = usuarioService.buscarUsuarioPorId(id);

        if(uFound != null){

            usuarioService.eliminarUsuario(uFound);

            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //ESTE CONTROLADOR SE COMUNICARÁ CON EL SERVICE Y CON EL REST TEMPLATE PARA SOLICITAR LOS CARROS AL SERVICIO CORRESPONDIENTE

    //AGREGAMOS EL CIRCUIT BREAKER A CADA PETICIÓN DONDE LLAMEMOS A OTOR SERVICIO
    @CircuitBreaker(name="carrosCB", fallbackMethod = "fallbackGetCarros")
    @GetMapping("/carros/{id}")

    public ResponseEntity<List<Carro>> listarCarrosPorId(@PathVariable Integer id){

        Usuario uFound = usuarioService.buscarUsuarioPorId(id);

        if(uFound != null){

            List<Carro> carros = usuarioService.listarCarros(id);

            return new ResponseEntity<>(carros,HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //ESTE CONTROLADOR FUNCIONARÁ IGUAL QUE EL DE CARROS PARA SOLICITAR LAS MOTOS AL SERVICIO
    //AGREGAMOS EL CIRCUIT BREAKER A CADA PETICIÓN DONDE LLAMEMOS A OTOR SERVICIO
    @CircuitBreaker(name="motosCB",fallbackMethod = "fallbackGetMotos")
    @GetMapping("/motos/{id}")

    public ResponseEntity<List<Moto>> listarMotosPorId(@PathVariable Integer id){

        Usuario uFound = usuarioService.buscarUsuarioPorId(id);

        if(uFound != null){

            List<Moto> motos = usuarioService.listarMotos(id);

            return new ResponseEntity<>(motos,HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //GUARDAR UN CARRO SEGÚN EL ID DEL USUARIO
    //AGREGAMOS EL CIRCUIT BREAKER A CADA PETICIÓN DONDE LLAMEMOS A OTOR SERVICIO
    @CircuitBreaker(name="carrosCB", fallbackMethod = "fallbackSaveCarro")
    @PostMapping("/carro/agregar/{id}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable Integer id, @RequestBody Carro c){

            Carro cSaved = usuarioService.guardarCarro(c,id);

            if(cSaved != null){

                return new ResponseEntity<>(cSaved,HttpStatus.CREATED);
            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

    }

    //GUARDAR UNA MOTO SEGÚN EL ID DEL USUARIO
    //AGREGAMOS EL CIRCUIT BREAKER A CADA PETICIÓN DONDE LLAMEMOS A OTOR SERVICIO
    @CircuitBreaker(name="motosCB",fallbackMethod = "fallbackSaveMoto")
    @PostMapping("/moto/agregar/{id}")

    public ResponseEntity<Moto> guardarMoto(@PathVariable Integer id, @RequestBody Moto m){

        Moto mSaved = usuarioService.guardarMoto(m,id);

        if(mSaved != null){

            return new ResponseEntity<>(mSaved,HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //AGREGAMOS EL CIRCUIT BREAKER A CADA PETICIÓN DONDE LLAMEMOS A OTOR SERVICIO
    @CircuitBreaker(name="vehiculosCB",fallbackMethod = "fallbackGetVehiculos")
    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<Map<String, Object>> listarVehiculosDelUsuario(@PathVariable Integer id){

        Map<String,Object> datos = usuarioService.obtenerUsuarioYVehiculos(id);

        if(datos != null && !datos.isEmpty()){

            return new ResponseEntity<>(datos,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //---MÉTODOS PARA TOLERANCIA DE FALLOS LLAMADOS POR EL CIRCUIT BREAKER AL HABER UN ERROR EN ALGÚNO DE LOS SERVICIOS LLAMADOS---

    //ERROR EN EL MÉTODO listarCarrosPorId
    private ResponseEntity<?> fallbackGetCarros(@PathVariable Integer id,RuntimeException ex){

     return new ResponseEntity<>("¡El usuario: " + id +" tiene sus carros en el taller!", HttpStatus.OK );

    }

    //ERROR EN EL MÉTODO guardarCarro
    private ResponseEntity<?> fallbackSaveCarro(@PathVariable Integer id,@RequestBody Carro c, RuntimeException ex){

        return new ResponseEntity<>("¡El usuario: " + id +" no tiene más espacio en su garaje para otro carro!", HttpStatus.OK );

    }

    //ERROR EN EL MÉTODO listarMotosPorId
    private ResponseEntity<?> fallbackGetMotos(@PathVariable Integer id,RuntimeException ex){

        return new ResponseEntity<>("¡El usuario: " + id +" tiene sus motos en el taller!", HttpStatus.OK );

    }

    //ERROR EN EL MÉTODO guardarMoto
    private ResponseEntity<?> fallbackSaveMoto(@PathVariable Integer id,@RequestBody Moto m,RuntimeException ex){

        return new ResponseEntity<>("¡El usuario: " + id +" no tiene más espacio en us garaje para otra moto!", HttpStatus.OK );

    }

    //ERROR EN EL MÉTODO listarVehiculosDelUsuario
    private ResponseEntity<?> fallbackGetVehiculos(@PathVariable Integer id,RuntimeException ex){

        return new ResponseEntity<>("¡El usuario: " + id +" dejó todos sus vehículos resguardados en su taller!", HttpStatus.OK );

    }


}
