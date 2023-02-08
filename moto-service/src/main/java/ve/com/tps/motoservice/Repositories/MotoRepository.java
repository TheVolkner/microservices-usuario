package ve.com.tps.motoservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.tps.motoservice.Entities.Moto;

import java.util.List;

public interface MotoRepository extends JpaRepository<Moto,Integer> {

    public List<Moto> findByUsuario(Integer id);
}
