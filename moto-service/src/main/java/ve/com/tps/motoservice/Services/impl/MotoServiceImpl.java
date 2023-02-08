package ve.com.tps.motoservice.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ve.com.tps.motoservice.Entities.Moto;
import ve.com.tps.motoservice.Repositories.MotoRepository;
import ve.com.tps.motoservice.Services.MotoService;

import java.util.List;
import java.util.Optional;

@Service
public class MotoServiceImpl implements MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Override
    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }

    @Override
    public Moto guardarMoto(Moto m) {
        return motoRepository.save(m);
    }

    @Override
    public Moto modificarMoto(Moto m) {

        Optional<Moto> mFound = motoRepository.findById(m.getId());

        if(mFound.isPresent()){

            return motoRepository.save(m);

        } else {

            return null;
        }
    }

    @Override
    public void eliminarMoto(Integer id) {

        Optional<Moto> mFound = motoRepository.findById(id);

        if(mFound.isPresent()){

            motoRepository.deleteById(id);

        }

    }

    @Override
    public Moto buscarMotoPorId(Integer id) {

        Optional<Moto> mFound = motoRepository.findById(id);

        return mFound.orElse(null);
    }

    @Override
    public List<Moto> listarMotosPorIdUsuario(Integer id) {

        return motoRepository.findByUsuario(id);
    }
}
