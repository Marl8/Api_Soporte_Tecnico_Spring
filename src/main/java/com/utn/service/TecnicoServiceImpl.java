package com.utn.service;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.dto.request.TecnicoUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseTecnicoDto;
import com.utn.entity.EEstado;
import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import com.utn.entity.Tecnico;
import com.utn.exception.EspecialidadNotFoundException;
import com.utn.exception.TecnicoNotFoundException;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.TecnicoRepository;
import com.utn.service.Interfaces.ITecnicoService;
import com.utn.utils.TecnicoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TecnicoServiceImpl implements ITecnicoService {

    TecnicoRepository repository;

    EspecialidadRepository especialidadRepository;

    IncidenteRepository incidenteRepository;

    public TecnicoServiceImpl(TecnicoRepository repository, EspecialidadRepository esp,
                              IncidenteRepository inc) {
        this.repository = repository;
        this.especialidadRepository = esp;
        this.incidenteRepository = inc;
    }

    @Override
    public ResponseTecnicoDto guardar(TecnicoDto tecnicoDto) {
        ModelMapper mapper = new ModelMapper();
        Tecnico tecnico = TecnicoMapper.tecnicoSaveMapper(tecnicoDto);

        Optional<Tecnico> encontrado = repository.findTecnicoByNombreAndApellido(
                tecnico.getNombre(), tecnico.getApellido());

        if(encontrado.isPresent()) {
            throw new TecnicoNotFoundException("Ya existe el técnico", HttpStatus.BAD_REQUEST);
        }
        Set<Especialidad> listaEspecialidades = new HashSet<>();
        tecnicoDto.getListaEspecialidades().forEach(e -> {
            Especialidad esp = especialidadRepository.findById(e).orElseThrow(() ->
                    new EspecialidadNotFoundException("Especialidad Not found", HttpStatus.NOT_FOUND));
            listaEspecialidades.add(esp);
        });
        tecnico.setListaEspecialidades(listaEspecialidades);
        repository.save(tecnico);
        TecnicoDto response = mapper.map(tecnico,TecnicoDto.class);
        return new ResponseTecnicoDto(response, "Técnico guardado con éxito");
    }

    @Override
    public ResponseDto asignarEspecialidadATecnico(Long idEspecialidad, Long idTecnico) {
        Especialidad esp = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EspecialidadNotFoundException("Especialidad no encontrada", HttpStatus.NOT_FOUND));
        Tecnico tecnico = repository.findById(idTecnico)
                .orElseThrow(() -> new TecnicoNotFoundException("Técnico no encontrado", HttpStatus.NOT_FOUND));
        tecnico.getListaEspecialidades().add(esp);
        repository.save(tecnico);
        return new ResponseDto("Asignación realizada con éxito");
    }
    @Override
    public TecnicoDto findTecnico(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new TecnicoNotFoundException("No existen técnicos con este id.", HttpStatus.NOT_FOUND);
        }
        Tecnico tecnico = repository.findById(id).get();
        return mapper.map(tecnico, TecnicoDto.class);
    }

    @Override
    public Set<TecnicoFindDto> findAll() {
        ModelMapper mapper = new ModelMapper();
        List<Tecnico> tecnicos = repository.findAll();
        Set<Tecnico> result = new HashSet<>(tecnicos);
        return result.stream().map(t -> new TecnicoFindDto(t.getId(),
                t.getNombre(), t.getApellido(), t.isDisponibilidad()))
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public ResponseTecnicoDto modificar(TecnicoUpdateDto tecnicoDto) {
        ModelMapper mapper = new ModelMapper();
        Tecnico tecnico = mapper.map(tecnicoDto, Tecnico.class);
        Tecnico encontrado = repository.findById(tecnico.getId())
                .orElseThrow(() -> new TecnicoNotFoundException("Técnico Not found", HttpStatus.NOT_FOUND));
        encontrado.setNombre(tecnico.getNombre());
        encontrado.setApellido(tecnico.getApellido());
        encontrado.setDisponibilidad(tecnico.isDisponibilidad());
        encontrado.setIncidentes(tecnico.getIncidentes());
        encontrado.setListaEspecialidades(tecnico.getListaEspecialidades());
        encontrado.setNotificacion(tecnico.getNotificacion());

        Tecnico modificado = repository.save(encontrado);
        TecnicoDto respuesta = mapper.map(modificado, TecnicoDto.class);
        return new ResponseTecnicoDto(respuesta, "Técnico modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Tecnico> tecnico = repository.findById(id);

        if(tecnico.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Técnico eliminado con éxito");
    }

    @Override
    public ResponseTecnicoDto tecnicoConMasResueltos(long dias){
        /*
        * Obtengo la fecha de búsqueda (los últimos N días) restado a la fecha actual
        * la cantidad de días que se desea retroceder en la búsqueda.
        *
        * https://parzibyte.me/blog/2018/10/16/sumar-restar-fechas-java/
        **/
        LocalDate fechaBusqueda = LocalDate.now().minusDays(dias);

        List<Incidente> incidentes = incidenteRepository.findIncidenteByEstadoAndFecha(fechaBusqueda, EEstado.RESUELTO);

        // Recorro la lista de incidentes y agrupo por cantidad de incidentes resueltos por cada técnico
        Map<Tecnico, Long> resueltos = incidentes.stream()
                .collect(Collectors.groupingBy(Incidente::getTecnico, Collectors.counting()));

        // Obtengo el técnico con más incidentes resueltos
        Tecnico tecnicoConMasIncidentes = resueltos.entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);

        ModelMapper mapper = new ModelMapper();
        TecnicoDto tecnicoDto = mapper.map(tecnicoConMasIncidentes, TecnicoDto.class);
        return new ResponseTecnicoDto(tecnicoDto, "El Técnico con más incidentes resueltos es: "
                + tecnicoDto.getNombre() + " " + tecnicoDto.getApellido());
    }
}
