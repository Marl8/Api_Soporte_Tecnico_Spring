package com.utn.service;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.dto.request.TecnicoCompleteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseTecnicoDto;
import com.utn.entity.EEstado;
import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import com.utn.entity.Tecnico;
import com.utn.exception.EspecialidadNotFoundException;
import com.utn.exception.IncidenteNotFoundException;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Tecnico guardado = repository.save(tecnico);
        return new ResponseTecnicoDto("El técnico " + guardado.getNombre() + " " + guardado.getApellido() + " fue guardado con éxito");
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
    public TecnicoCompleteDto findTecnico(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new TecnicoNotFoundException("No existen técnicos con este id.", HttpStatus.NOT_FOUND);
        }
        Tecnico tecnico = repository.findById(id).get();
        return mapper.map(tecnico, TecnicoCompleteDto.class);
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
    public ResponseTecnicoDto modificar(TecnicoCompleteDto tecnicoDto) {
        ModelMapper mapper = new ModelMapper();
        Tecnico tecnico = mapper.map(tecnicoDto, Tecnico.class);
        Tecnico encontrado = repository.findById(tecnico.getId())
                .orElseThrow(() -> new TecnicoNotFoundException("Técnico Not found", HttpStatus.NOT_FOUND));
        encontrado.setNombre(tecnico.getNombre());
        encontrado.setApellido(tecnico.getApellido());
        encontrado.setDisponibilidad(tecnico.isDisponibilidad());
        encontrado.setListaEspecialidades(tecnico.getListaEspecialidades());
        encontrado.setNotificacion(tecnico.getNotificacion());

        Tecnico modificado = repository.save(encontrado);
        return new ResponseTecnicoDto("El técnico " + modificado.getNombre() + " " + modificado.getApellido() +
                " fue modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Tecnico> tecnico = repository.findById(id);

        if(tecnico.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Técnico eliminado con éxito");
    }

    /**
     * Método para encontrar al técnico que más incidentes resolvió en los últimos N días.
     * @param dias El rango de los últimos días para realizar la búsqueda
     * */
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
        TecnicoCompleteDto tecnicoDto = mapper.map(tecnicoConMasIncidentes, TecnicoCompleteDto.class);
        return new ResponseTecnicoDto("El Técnico con más incidentes resueltos es: "
                + tecnicoDto.getNombre() + " " + tecnicoDto.getApellido());
    }

    /**
     * Método pra encontrar al técnico que más incidentes resolvió en los últimos N días,
     * filtrando por especialidad.
     * @param dias El rango de los últimos días para realizar la búsqueda
     * @param especialidad La especialidad por la que se desea filtrar.
     * */
    @Override
    public ResponseTecnicoDto tecnicoEspecialidadMasResueltos(long dias, String especialidad) {

        LocalDate fechaBusqueda = LocalDate.now().minusDays(dias);

        List<Incidente> incidentes = incidenteRepository.findIncidenteByEstadoAndFecha(fechaBusqueda, EEstado.RESUELTO);
        List<Incidente> incidentesPorEspecialidad = new ArrayList<>();
        Tecnico tecnicoConMasIncidentes;

        // Filtro los incidentes resueltos de acuerdo a la especialidad requerida
        for(Incidente incidente : incidentes){
            for(Especialidad esp : incidente.getTecnico().getListaEspecialidades()){
                if(esp.getNombre().equals(especialidad)){
                    incidentesPorEspecialidad.add(incidente);
                }
            }
        }
        if(!incidentesPorEspecialidad.isEmpty()) {
            // Recorro la lista de incidentes y agrupo por cantidad de incidentes resueltos por cada técnico
            Map<Tecnico, Long> resueltos = incidentesPorEspecialidad.stream()
                    .collect(Collectors.groupingBy(Incidente::getTecnico, Collectors.counting()));

            // Obtengo el técnico con más incidentes resueltos
            tecnicoConMasIncidentes = resueltos.entrySet().stream()
                    .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        }else{
            throw new IncidenteNotFoundException("No se encontraron incidentes resueltos la especialidad: " + especialidad,
                    HttpStatus.BAD_REQUEST);
        }
        ModelMapper mapper = new ModelMapper();
        TecnicoCompleteDto tecnicoDto = mapper.map(tecnicoConMasIncidentes, TecnicoCompleteDto.class);
        return new ResponseTecnicoDto("El Técnico con más incidentes resueltos de la especialidad " +
                especialidad + " es: " + tecnicoDto.getNombre() + " " + tecnicoDto.getApellido());
    }

    /**
     * Método pra encontrar al técnico que más rápido resolvió un incidente.
     * */
    @Override
    public ResponseTecnicoDto tecnicoMasRapido(){
        List<Incidente> incidentes = incidenteRepository.findIncidenteByEstado(EEstado.RESUELTO);

        LocalDateTime fechaInicial;
        LocalDateTime fechaResolucion;
        Map<Incidente, Long> incidentesMap = new HashMap<>();

        // Recorro la lista de incidentes convierto las fechas de creación y de cierre en cantidad de horas y las guardo en un MAP
        for(Incidente incidente : incidentes){
            fechaInicial = incidente.getFechaCreacion();
            fechaResolucion = incidente.getFechaCierre();
            Duration duration = Duration.between(fechaInicial, fechaResolucion);

            incidentesMap.put(incidente, duration.toHours());
        }

        // Compara las horas que tardo en resolverse cada incidente y obtiene el incidente con menos horas de resolución
        Incidente incidenteMasRapido = incidentesMap.entrySet().stream()
                .min(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                .orElseThrow( () -> new IncidenteNotFoundException("No se ha podido completar la operación",
                        HttpStatus.INTERNAL_SERVER_ERROR));

        // Incidente obtengo el técnico asociado que fue el técnico más rápido
        Tecnico tecnicoMasRapido = incidenteMasRapido.getTecnico();

        ModelMapper mapper = new ModelMapper();
        TecnicoCompleteDto dto = mapper.map(tecnicoMasRapido, TecnicoCompleteDto.class);
        return new ResponseTecnicoDto("El técnico que más rápido resolvió un incidente" +
                "fue: " + tecnicoMasRapido.getNombre() + " " + dto.getApellido());
    }
}
