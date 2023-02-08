package ve.com.tps.usuarioservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ve.com.tps.usuarioservice.models.Carro;
import ve.com.tps.usuarioservice.models.Moto;

import java.util.List;

//HACEMOS UNA CLASE LA CÚAL ESTARÁ ENCARGAD DE SER EL FEIGN CLIENT PARA EL SERVICIO DE MOTO
//LE INDICAMOS UN NOMBRE Y EL PATH BASE PARA HACER LAS PETICIONES, JUNTO CON EL REQUEST MAPPING PARA
//INDICAR EL NOMBRE DE LA API
@FeignClient(name = "moto-service" , url="http://localhost:8003" , path = "/api/moto")
public interface MotoFeignClient {

    @PostMapping("/agregar")
    public Moto save(@RequestBody Moto moto);

    @GetMapping("/usuario/{id}")
    public List<Moto> listarMotos(@PathVariable Integer id);
}
