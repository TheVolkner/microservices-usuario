package ve.com.tps.usuarioservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import ve.com.tps.usuarioservice.entities.Usuario;
import ve.com.tps.usuarioservice.models.Carro;
import ve.com.tps.usuarioservice.models.Moto;
import ve.com.tps.usuarioservice.repository.UsuarioRepository;

import java.util.List;
import java.util.Map;

public interface UsuarioService {
    public List<Usuario> listarUsuarios();

    public Usuario agregarUsuario(Usuario u);

    public Usuario buscarUsuarioPorId(Integer id);

    public Usuario modificarUsuario(Usuario u);

    public void eliminarUsuario(Usuario u);

    public List<Carro> listarCarros(Integer id);

    public Carro guardarCarro(Carro c,Integer idUsuario);

    public List<Moto> listarMotos(Integer id);

    public Moto guardarMoto(Moto m, Integer idUsuario);

    public Map<String,Object> obtenerUsuarioYVehiculos(Integer idUsuario);

}
