package com.utn.service;

import com.utn.dto.request.TipoProblemaDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseProblemaDto;
import com.utn.entity.TipoProblema;
import com.utn.repository.ProblemaRepository;
import com.utn.service.Interfaces.IProblemaService;
import com.utn.utils.TipoProblemaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProblemaServiceImpl implements IProblemaService {

    ProblemaRepository repository;

    public ProblemaServiceImpl(ProblemaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseProblemaDto guardar(TipoProblemaDto problemaDto) {
        ModelMapper mapper = new ModelMapper();
        TipoProblema problema = TipoProblemaMapper.tipoProblemaSaveMapper(problemaDto);

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
        TipoProblema problema = repository.findById(id).orElseThrow(
                () -> new RuntimeException("No existen problemas con este id."));
        return mapper.map(problema, TipoProblemaDto.class);
    }


    @Override
    public ResponseProblemaDto modificar(TipoProblemaDto problemaDto) {
        ModelMapper mapper = new ModelMapper();
        TipoProblema problema = mapper.map(problemaDto, TipoProblema.class);
        TipoProblema encontrado = repository.findById(problema.getId())
                .orElseThrow(() -> new RuntimeException("No se encontraron problemas asociados a este id"));

            encontrado.setEspecialidad(problema.getEspecialidad());
            encontrado.setDescripcion(problema.getDescripcion());
            encontrado.setTiempoEstimado(problema.getTiempoEstimado());
            encontrado.setIncidente(problema.getIncidente());

        TipoProblema problem = repository.save(encontrado);
        TipoProblemaDto respuesta = mapper.map(problem, TipoProblemaDto.class);
        return new ResponseProblemaDto(respuesta, "Tipo de problema modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        TipoProblema problema = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Tipo de problema no encontrado"));
        repository.deleteById(id);
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
