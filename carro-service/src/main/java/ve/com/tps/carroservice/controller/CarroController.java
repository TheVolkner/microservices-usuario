package ve.com.tps.carroservice.controller;

import ve.com.tps.carroservice.entities.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.carroservice.services.CarroService;

import java.util.List;

@RestController
@RequestMapping("/api/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping()
    public ResponseEntity<List<Carro>> listarCarros(){

        List<Carro> listaCarros = carroService.listarCarros();

        if(listaCarros != null && !listaCarros.isEmpty()){

            return new ResponseEntity<>(listaCarros,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> listarCarroPorId(@PathVariable Integer id){

        Carro cFound = carroService.buscarCarroPorId(id);

        if(cFound != null){

            return new ResponseEntity<>(cFound,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Carro>> listarCarrosPorIdUsuario(@PathVariable Integer id){

        List<Carro> listaCarros = carroService.listarCarrosPorIdUsuario(id);

        if(listaCarros != null && !listaCarros.isEmpty()){

            return new ResponseEntity<>(listaCarros,HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<Carro> agregarCarro(@RequestBody Carro c){

        Carro cSaved = carroService.agregarCarro(c);

        if(cSaved != null){

            return new ResponseEntity<>(cSaved, HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/modificar")
    public ResponseEntity<Carro> modificarCarro(@PathVariable Integer id,@RequestBody Carro c){

        Carro cFound = carroService.buscarCarroPorId(id);

        if(cFound != null){

            c.setId(id);
            Carro cSaved = carroService.modificarCarro(c);
            return new ResponseEntity<>(cSaved,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/borrar")
    public ResponseEntity<Carro> eliminarCarro(@PathVariable Integer id){

        Carro cFound = carroService.buscarCarroPorId(id);

        if(cFound != null){

            carroService.borrarCarro(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
