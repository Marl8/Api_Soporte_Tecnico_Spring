package com.utn.service;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.request.OperadorUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseOperadorDto;
import com.utn.entity.*;
import com.utn.exception.IncidenteNotFoundException;
import com.utn.exception.OperadorNotFoundException;
import com.utn.exception.TecnicoNotFoundException;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.OperadorRepository;
import com.utn.repository.TecnicoRepository;
import com.utn.service.Interfaces.IOperadorService;
import com.utn.utils.OperadorMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperadorServiceImpl implements IOperadorService {

    OperadorRepository repository;

    IncidenteRepository incidenteRepository;

    TecnicoRepository tecnicoRepository;


    public OperadorServiceImpl(OperadorRepository repository, IncidenteRepository incidenteRepository
    ,TecnicoRepository tecnicoRepository) {
        this.repository = repository;
        this.incidenteRepository = incidenteRepository;
        this.tecnicoRepository = tecnicoRepository;

    }

    @Override
    public ResponseOperadorDto guardar(OperadorDto operadorDto) {
        Operador operador = OperadorMapper.operador(operadorDto);

        if(verificarSiExiste(operador)){
            throw new OperadorNotFoundException("El operador ya existe.", HttpStatus.NOT_FOUND);
        }
        Operador guardado = repository.save(operador);
        return new ResponseOperadorDto(guardado.getNombre(),  guardado.getApellido(), "Operador guardado con éxito.");
    }

    @Override
    public OperadorDto findOperador(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new OperadorNotFoundException("No existen operadores con ese id.", HttpStatus.NOT_FOUND);
        }
        Operador operador = repository.findById(id).get();
        return mapper.map(operador, OperadorDto.class);
    }

    @Override
    public ResponseOperadorDto modificar(OperadorUpdateDto operadorDto, Long id) {
        ModelMapper mapper = new ModelMapper();
        Operador operador = mapper.map(operadorDto, Operador.class);
        Operador encontrado = repository.findById(id)
                .orElseThrow(() -> new OperadorNotFoundException("No existen operadores con ese id.", HttpStatus.NOT_FOUND));

        encontrado.setNombre(operador.getNombre());
        encontrado.setApellido(operador.getApellido());
        encontrado.setListaTecnicos(operador.getListaTecnicos());
        Operador modificado = repository.save(encontrado);

        return new ResponseOperadorDto(modificado.getNombre(),modificado.getApellido(), "Operador modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Operador> servicio = repository.findById(id);

        if(servicio.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Operador eliminado con éxito");
    }

    /**
     * Método para asignarle un técnico al incidente.
     * En primer lugar, se le asigna un operador de forma aleatoria y este es el encargado
     * de asignar un técnico de la lista de técnicos disponibles.
     * @param idIncidente Id del incidente
     * */
    @Transactional
    @Override
    public ResponseDto asignarTecnico(Long idIncidente) {
        Incidente incident = incidenteRepository.findById(idIncidente)
                .orElseThrow(() -> new IncidenteNotFoundException("Incidente not found", HttpStatus.NOT_FOUND));
        List<Operador> listaOperadores = repository.findAll();
        Operador operador;

        // Se asigna un operador de forma aleatoria
        if (!listaOperadores.isEmpty()) {
            Collections.shuffle(listaOperadores);
            operador = listaOperadores.get(0);
            incident.setOperador(operador);
        }else {
            throw new OperadorNotFoundException("No se han encontrado operadores", HttpStatus.NOT_FOUND);
        }

        // Se le asigna al incidente un técnico de forma aleatoria según el tipo de problema
        List<Tecnico> listTecnicosEspIncidente = tecnicosEspecialidadesIncidente(incident);
        List<Tecnico> tecnicosDisponibles = new ArrayList<>(listTecnicosEspIncidente.stream().filter(Tecnico::isDisponibilidad).toList());
        Tecnico tecnico;

        if (!tecnicosDisponibles.isEmpty()) {
            Collections.shuffle(tecnicosDisponibles);
            tecnico = tecnicosDisponibles.get(0);
            tecnico.setDisponibilidad(false);
            incident.setTecnico(tecnico);
        }else {
            throw new TecnicoNotFoundException("No se han encontrado técnicos disponibles", HttpStatus.NOT_FOUND);
        }
        incident.modificarEstado();
        incidenteRepository.save(incident);
        return new ResponseDto("Se ha asignado el técnico con éxito");
    }

    /**
     * Método que genera la lista de técnicos según la especialidad requerida por el
     * incidente de acuerdo a los tipos de problemas asociados.
     * @param incident Recibe como parámetro el incidente
     * */
    public List<Tecnico> tecnicosEspecialidadesIncidente(Incidente incident) {
        List<Especialidad> listaEspecialidadesIncidente = new ArrayList<>();

        // Se genera la lista de las especialidades requeridas según los tipos de problemas reportados en el incidente.
        incident.getListaProblemas().forEach(p -> listaEspecialidadesIncidente.add(p.getEspecialidad()));

        List<Tecnico> listaTecnicos = tecnicoRepository.findAll();
        List<Tecnico> listaEspecialidad;

       // Se genera una lista con los técnicos que tengan dichas especialidades.
        if(!listaTecnicos.isEmpty()) {
            listaEspecialidad = listaTecnicos.stream()
                        .filter(tecnico -> tecnico.getListaEspecialidades().stream()
                                .anyMatch(listaEspecialidadesIncidente::contains))
                        .collect(Collectors.toList());
        }else {
            throw new TecnicoNotFoundException("Not found técnicos", HttpStatus.BAD_REQUEST);
        }
        return listaEspecialidad;
    }

    /*
    * Se Agrega la funcionalidad para ampliar el plazo de resolución si la problemática
    * asociada al incidente es compleja.
    **/
    @Override
    public ResponseDto asignarHorasColchon(Long idIncidente, int horas){
        Incidente incidente = incidenteRepository.findById(idIncidente)
                .orElseThrow(() -> new IncidenteNotFoundException("Incidente Not found", HttpStatus.NOT_FOUND));
        incidente.setEsComplejo(true);
        incidente.setHoraColchon(horas);
        int nuevoTiempoEstimado = incidente.getTiempoResolucion() + horas;
        incidente.setTiempoResolucion(nuevoTiempoEstimado);
        incidenteRepository.save(incidente);

        return new ResponseDto("Tiempo de resolución ampliado con éxito");
    }

    /**
     * Método para asignarle a un nuevo Operador la lista de técnicos existentes.
     * */
    @Override
    public ResponseDto asignarListaTecnico(Long idOperador) {
        Operador operador = repository.findById(idOperador)
                .orElseThrow(() -> new OperadorNotFoundException("Operador Not found", HttpStatus.NOT_FOUND));
        List<Tecnico> listaTecnicos = tecnicoRepository.findAll();
        Set<Tecnico> result = new HashSet<>(listaTecnicos);
        if(!listaTecnicos.isEmpty()){
            operador.setListaTecnicos(result);
            repository.save(operador);
        }else{
            throw new TecnicoNotFoundException("No se han encontrado técnicos", HttpStatus.NOT_FOUND);
        }
        return new ResponseDto("Se ha asignado con éxito la lista de técnicos");
    }

    private boolean verificarSiExiste(Operador operador){

        List<Operador> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Operador> listaBusqueda = lista.stream()
                .filter(o -> o.getNombre().equals(operador.getNombre())
                        && o.getApellido().equals(operador.getApellido()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }
}
