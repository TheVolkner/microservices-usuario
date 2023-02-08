package ve.com.tps.usuarioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.tps.usuarioservice.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {


}
