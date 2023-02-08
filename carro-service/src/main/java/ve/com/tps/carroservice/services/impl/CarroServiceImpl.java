package ve.com.tps.carroservice.services.impl;

import ve.com.tps.carroservice.entities.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ve.com.tps.carroservice.repositories.CarroRepository;
import ve.com.tps.carroservice.services.CarroService;

import java.util.List;
import java.util.Optional;

@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public List<Carro> listarCarros() {
        return carroRepository.findAll();
    }

    @Override
    public Carro agregarCarro(Carro c) {
        return carroRepository.save(c);
    }

    @Override
    public Carro modificarCarro(Carro c) {

        Optional<Carro> cFound = carroRepository.findById(c.getId());

        if(cFound.isPresent()){

            return carroRepository.save(c);
        } else {

            return null;
        }

    }

    @Override
    public Carro buscarCarroPorId(Integer id) {

        Optional<Carro> cFound = carroRepository.findById(id);

        return cFound.orElse(null);

    }

    @Override
    public void borrarCarro(Integer id) {

        Optional<Carro> cFound = carroRepository.findById(id);

        if(cFound.isPresent()){

            carroRepository.deleteById(id);
        }

    }

    @Override
    public List<Carro> listarCarrosPorIdUsuario(Integer idUsuario) {

        return carroRepository.findByUsuario(idUsuario);
    }
}
