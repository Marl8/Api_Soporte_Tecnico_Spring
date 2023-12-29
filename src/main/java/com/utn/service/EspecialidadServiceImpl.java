package com.utn.service;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.request.EspecialidadFindDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseEspecialidadDto;
import com.utn.entity.Especialidad;
import com.utn.entity.TipoProblema;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.ProblemaRepository;
import com.utn.service.Interfaces.IEspecialidadService;
import com.utn.utils.EspecialidadMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EspecialidadServiceImpl implements IEspecialidadService {

    private final EspecialidadRepository repository;

    private final ProblemaRepository problemaRepository;


    public EspecialidadServiceImpl(EspecialidadRepository repository, ProblemaRepository probl) {
        this.repository = repository;
        this.problemaRepository = probl;
    }

    @Override
    public ResponseEspecialidadDto guardar(EspecialidadDto especialidadDto) {
        ModelMapper mapper = new ModelMapper();
        Especialidad especialidad = EspecialidadMapper.especialidadSaveMapper(especialidadDto);

        if(verificarSiExiste(especialidad)){
            throw new RuntimeException("La especialidad ya existe.");
        }
        Set<TipoProblema> problemas = new HashSet<>();
        especialidadDto.getListaProblemas().forEach(p -> {
            TipoProblema problema = problemaRepository.findById(p).orElseThrow(
                    () -> new RuntimeException("No existen problemas con este id."));
            problemas.add(problema);
        });
        especialidad.setListaProblemas(problemas);
        repository.save(especialidad);
        EspecialidadDto response = mapper.map(especialidad, EspecialidadDto.class);
        return new ResponseEspecialidadDto(response, "Especialidad guardada con éxito");
    }

    @Override
    public ResponseDto asignarProblema(Long idEspecialidad, Long idProblema) {
        Especialidad esp = repository.findById(idEspecialidad)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        TipoProblema problema = problemaRepository.findById(idProblema)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        esp.getListaProblemas().add(problema);
        repository.save(esp);
        return new ResponseDto("Asignación realizada con éxito");
    }

    @Override
    public EspecialidadFindDto findEspecialidad(Long id) {
        ModelMapper mapper = new ModelMapper();
        Especialidad esp = repository.findById(id).orElseThrow(
                () -> new RuntimeException("No existen especialidades con este id."));
        return mapper.map(esp, EspecialidadFindDto.class);
    }

    @Override
    public Set<EspecialidadFindDto> findAll() {
        ModelMapper mapper = new ModelMapper();
        List<Especialidad> especialidades = repository.findAll();
        return EspecialidadMapper.especialidadFindAllMapper(especialidades);
    }

    @Override
    public ResponseEspecialidadDto modificar(EspecialidadDto especialidadDto) {
        ModelMapper mapper = new ModelMapper();
        Especialidad esp = mapper.map(especialidadDto, Especialidad.class);
        Especialidad encontrado = repository.findById(esp.getId())
                .orElseThrow(() -> new RuntimeException("Especialidad inexistente."));

            encontrado.setNombre(esp.getNombre());
            encontrado.setDescripcion(esp.getDescripcion());
            encontrado.setListaTecnicos(esp.getListaTecnicos());
            encontrado.setListaProblemas(esp.getListaProblemas());

            Especialidad especialidad = repository.save(encontrado);
        EspecialidadDto respuesta = mapper.map(especialidad, EspecialidadDto.class);
        return new ResponseEspecialidadDto(respuesta, "Servicio modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Especialidad> servicio = repository.findById(id);

        if(servicio.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Servicio eliminado con éxito");
    }


    private boolean verificarSiExiste(Especialidad especialidad){

        List<Especialidad> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Especialidad> listaBusqueda = lista.stream()
                .filter(e -> e.getNombre().equals(especialidad.getNombre())
                        && e.getDescripcion().equals(especialidad.getDescripcion()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }
}
