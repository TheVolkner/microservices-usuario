package ve.com.tps.carroservice.services;

import ve.com.tps.carroservice.entities.Carro;

import java.util.List;

public interface CarroService {

    public List<Carro> listarCarros();

    public Carro agregarCarro(Carro c);

    public Carro modificarCarro(Carro c);

    public Carro buscarCarroPorId(Integer id);

    public void borrarCarro(Integer id);

    public List<Carro> listarCarrosPorIdUsuario(Integer idUsuario);
}
