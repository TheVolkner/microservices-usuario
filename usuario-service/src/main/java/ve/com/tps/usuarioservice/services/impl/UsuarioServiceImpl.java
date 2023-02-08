package ve.com.tps.usuarioservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ve.com.tps.usuarioservice.entities.Usuario;
import ve.com.tps.usuarioservice.feignclients.CarroFeignClient;
import ve.com.tps.usuarioservice.feignclients.MotoFeignClient;
import ve.com.tps.usuarioservice.models.Carro;
import ve.com.tps.usuarioservice.models.Moto;
import ve.com.tps.usuarioservice.repository.UsuarioRepository;
import ve.com.tps.usuarioservice.services.UsuarioService;

import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    //REST TEMPLATE ES UNA FORMA DE COMUNICAR MICROSERVICIOS
    @Autowired
    private RestTemplate restTemplate;

    //FEIGN CLIENT ES UNA HERRAMIENTA PARA COMUNICAR MICROSERVICIOS
    //PROVISTA POR SPRING CLOUD
    @Autowired
    private UsuarioRepository usuarioRepository;

    //INYECTAMOS LOS FEIGN CLIENTS DE CARRO Y MOTO PARA MANDAR A LLAMAR LOS PATHS DE SU CONTROLADOR
    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    @Override
    public List<Usuario> listarUsuarios() {

        return usuarioRepository.findAll();
    }

    @Override
    public Usuario agregarUsuario(Usuario u) {

        return usuarioRepository.save(u);
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        return usuarioOptional.orElse(null);
    }

    @Override
    public Usuario modificarUsuario(Usuario u) {

        return usuarioRepository.save(u);
    }

    @Override
    public void eliminarUsuario(Usuario u) {

        usuarioRepository.delete(u);
    }

    //UTILIZAMOS EL REST TEMPLATE PARA COMUNICARNOS CON EL SERVICIO DE CARRO Y SOLICITAMOS LOS CARROS DEL USUARIO A SU CONTROLADOR
    @Override
    public List<Carro> listarCarros(Integer id) {
        List<Carro> carros = restTemplate.getForObject("http://localhost:8002/api/carro/usuario/" + id.toString(),List.class);
        if(carros != null && !carros.isEmpty()){

            return carros;
        } else {

            return new ArrayList<>();
        }
    }

    //AGREGAMOS UN CARRO HACIENDO USO DE FEIGN CLIENT
    @Override
    public Carro guardarCarro(Carro c, Integer idUsuario) {

        Optional<Usuario> uFound = usuarioRepository.findById(idUsuario);

        if(uFound.isPresent()){

            c.setUsuario(idUsuario);
            return carroFeignClient.save(c);
        } else {

            return null;
        }
    }


    //UTILIZAMOS EL REST TEMPLATE PARA COMUNICARNOS CON EL SERVICIO DE MOTO Y SOLICITAMOS LAS MOTOS DEL USUARIO A SU CONTROLADOR
    @Override
    public List<Moto> listarMotos(Integer id) {
        List<Moto> motos = restTemplate.getForObject("http://localhost:8003/api/moto/usuario/" + id.toString(),List.class);
        if(motos != null && !motos.isEmpty()){

            return motos;
        } else {

            return new ArrayList<>();
        }
    }

    //GUARDAMOS UNA MOTO CON EL FEIGN CLIENT LLAMANDO AL MOTO-SERVICE SEGUN EL ID DEL USUARIO
    @Override
    public Moto guardarMoto(Moto m, Integer idUsuario) {

        Optional<Usuario> uFound = usuarioRepository.findById(idUsuario);

        if(uFound.isPresent()){

            m.setUsuario(idUsuario);
            return motoFeignClient.save(m);
        } else {
            return null;
        }
    }

    //OBTENEMOS CON FEIGN CLIENT TODOS LOS VEHICULOS TANTO MOTOS COMO CARROS DE ESE USUARIO
    //DEVOLVEMOS EL RESULTADO COMO UN MAPA INDICANDO LOS DATOS DEL USUARIO Y DE SUS DOS TIPOS DE VEHICULOS
    @Override
    public Map<String, Object> obtenerUsuarioYVehiculos(Integer idUsuario) {

        Map<String,Object> resultado = new HashMap<>();

        Usuario uFound = usuarioRepository.findById(idUsuario).orElse(null);

        if(uFound != null){

            //SI EL USUARIO EXISTE, BUSCAMOS SUS MOTOS Y CARROS ASOCIADOS Y LOS ASOCIAMOS AL MAP
            resultado.put("Usuario",uFound);
            List<Carro> carros = carroFeignClient.listarCarros(idUsuario);
            if(carros != null && !carros.isEmpty()){
                resultado.put("Carros",carros);
            }else {
                resultado.put("Carros","El usuario no tiene carros");
            }
            List<Moto> motos = motoFeignClient.listarMotos(idUsuario);
            if(motos != null && !motos.isEmpty()){
                resultado.put("Motos",motos);
            } else {
                resultado.put("Motos","El usuario no tiene motos");
            }

            return resultado;

        } else {

            resultado.put("Mensaje de Error", "El usuario no existe");
            return resultado;
        }
    }

}
