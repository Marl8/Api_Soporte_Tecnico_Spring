package com.utn.service;

import com.utn.dto.request.TipoProblemaDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseProblemaDto;
import com.utn.entity.TipoProblema;
import com.utn.repository.ProblemaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemaServiceImpl implements IProblemaService{

    ProblemaRepository repository;

    public ProblemaServiceImpl(ProblemaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseProblemaDto guardar(TipoProblemaDto problemaDto) {
        ModelMapper mapper = new ModelMapper();
        TipoProblema problema = mapper.map(problemaDto, TipoProblema.class);

        if(verificarSiExiste(problema)){
            throw new RuntimeException("El tipo de problema ya existe.");
        }
        repository.save(problema);
        TipoProblemaDto response = mapper.map(problema, TipoProblemaDto.class);
        return new ResponseProblemaDto(response, "Problema guardado con éxito.");
    }

    @Override
    public TipoProblemaDto findProblema(Long id) {

        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen servicios con ese id.");
        }
        TipoProblema problema = repository.findById(id).get();
        return mapper.map(problema, TipoProblemaDto.class);
    }


    @Override
    public ResponseProblemaDto modificar(TipoProblemaDto problemaDto) {
        ModelMapper mapper = new ModelMapper();
        TipoProblema problema = mapper.map(problemaDto, TipoProblema.class);
        Optional<TipoProblema> encontrado = repository.findById(problema.getId());

        if (encontrado.isPresent()) {
            TipoProblema modificado = encontrado.get();
            modificado.setEspecialidad(problema.getEspecialidad());
            modificado.setDescripcion(problema.getDescripcion());
            modificado.setTiempoEstimado(problema.getTiempoEstimado());
            modificado.setIncidente(problema.getIncidente());
        }
        TipoProblemaDto respuesta = mapper.map(problema, TipoProblemaDto.class);
        return new ResponseProblemaDto(respuesta, "Tipo de problema modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<TipoProblema> problema = repository.findById(id);

        if(problema.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Problema eliminado con éxito");
    }

    private boolean verificarSiExiste(TipoProblema problema){

        List<TipoProblema> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<TipoProblema> listaBusqueda = lista.stream()
                .filter(p -> p.getDescripcion().equals(problema.getDescripcion())
                        && p.getEspecialidad().equals(problema.getEspecialidad()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }
}
