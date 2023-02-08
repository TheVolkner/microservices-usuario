package ve.com.tps.motoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.motoservice.Entities.Moto;
import ve.com.tps.motoservice.Services.MotoService;

import java.util.List;

@RestController
@RequestMapping("/api/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping()
    public ResponseEntity<List<Moto>> listarMotos(){

        List<Moto> motos = motoService.listarMotos();

        if(motos != null && !motos.isEmpty()){

            return new ResponseEntity<>(motos, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarMotoPorId(@PathVariable Integer id){

        Moto mFound = motoService.buscarMotoPorId(id);

        if(mFound != null){

            return new ResponseEntity<>(mFound,HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/agregar")
    public ResponseEntity<Moto> agregarMoto(@RequestBody Moto m){

        Moto mSaved = motoService.guardarMoto(m);

        if(mSaved != null){

            return new ResponseEntity<>(mSaved,HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/modificar")
    public ResponseEntity<Moto> modificarMoto(@PathVariable Integer id, @RequestBody Moto m){

        Moto mFound = motoService.buscarMotoPorId(id);

        if(mFound != null){

            m.setId(id);
            Moto mSaved = motoService.modificarMoto(m);
            return new ResponseEntity<>(mSaved,HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/borrar")
    public ResponseEntity<Moto> agregarMoto(@PathVariable Integer id){

        Moto mFound = motoService.buscarMotoPorId(id);

        if(mFound != null){

            motoService.eliminarMoto(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Moto>> listarMotosPorIdUsuario(@PathVariable Integer id){

        List<Moto> listaMotos= motoService.listarMotosPorIdUsuario(id);

        if(listaMotos != null && !listaMotos.isEmpty()){

            return new ResponseEntity<>(listaMotos,HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
