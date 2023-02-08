package ve.com.tps.motoservice.Services;

import ve.com.tps.motoservice.Entities.Moto;

import java.util.List;

public interface MotoService {

    public List<Moto> listarMotos();

    public Moto guardarMoto(Moto m);

    public Moto modificarMoto(Moto m);

    public void eliminarMoto(Integer id);

    public Moto buscarMotoPorId(Integer id);

    public List<Moto> listarMotosPorIdUsuario(Integer id);
}
