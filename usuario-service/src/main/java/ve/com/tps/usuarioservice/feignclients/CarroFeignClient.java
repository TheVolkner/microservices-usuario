package ve.com.tps.usuarioservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ve.com.tps.usuarioservice.models.Carro;

import java.util.List;

//HACEMOS UNA CLASE LA CÚAL ESTARÁ ENCARGAD DE SER EL FEIGN CLIENT PARA EL SERVICIO DE CARRO
//LE INDICAMOS UN NOMBRE Y EL PATH BASE PARA HACER LAS PETICIONES, JUNTO CON EL REQUEST MAPPING PARA
//INDICAR EL NOMBRE DE LA API
@FeignClient(name= "carro-service", path = "/api/carro")
public interface CarroFeignClient {

    //ESTE MÉTODO CONCATENA ESTE PATH CON EL PRINCIPAL Y MANDA A LLAMAR A ESA URL DEL SERVICIO CARRO
    @PostMapping("/agregar")
    public Carro save(@RequestBody Carro carro);

    @GetMapping("/usuario/{id}")
    public List<Carro> listarCarros(@PathVariable Integer id);
}
