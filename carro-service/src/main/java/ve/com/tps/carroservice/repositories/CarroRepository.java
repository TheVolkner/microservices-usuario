package ve.com.tps.carroservice.repositories;

import ve.com.tps.carroservice.entities.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro,Integer> {

    //LISTA LOS CARROS DEL SISTEMA SEGÚN EL ID DEL USUARIO AL CÚAL PERTENEZCAN.
    public List<Carro> findByUsuario(Integer usuario);
}
