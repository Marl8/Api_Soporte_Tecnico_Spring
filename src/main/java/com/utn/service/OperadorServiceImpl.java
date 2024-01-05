package com.utn.service;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.request.OperadorUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseOperadorDto;
import com.utn.entity.Incidente;
import com.utn.entity.Operador;
import com.utn.entity.Tecnico;
import com.utn.exception.IncidenteNotFoundException;
import com.utn.exception.OperadorNotFoundException;
import com.utn.exception.TecnicoNotFoundException;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.OperadorRepository;
import com.utn.repository.TecnicoRepository;
import com.utn.service.Interfaces.IOperadorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        ModelMapper mapper = new ModelMapper();
        Operador operador = mapper.map(operadorDto, Operador.class);

        if(verificarSiExiste(operador)){
            throw new OperadorNotFoundException("El operador ya existe.", HttpStatus.NOT_FOUND);
        }
        repository.save(operador);
        OperadorDto response = mapper.map(operador, OperadorDto.class);
        return new ResponseOperadorDto(response, "Operador guardado con éxito.");
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
    public ResponseOperadorDto modificar(OperadorUpdateDto operadorDto) {
        ModelMapper mapper = new ModelMapper();
        Operador operador = mapper.map(operadorDto, Operador.class);
        Operador encontrado = repository.findById(operador.getId())
                .orElseThrow(() -> new OperadorNotFoundException("No existen operadores con ese id.", HttpStatus.NOT_FOUND));

        encontrado.setNombre(operador.getNombre());
        encontrado.setApellido(operador.getApellido());
        encontrado.setListaTecnicos(operador.getListaTecnicos());
        repository.save(encontrado);

        OperadorDto respuesta = mapper.map(operador, OperadorDto.class);
        return new ResponseOperadorDto(respuesta, "Operador modificado con éxito");
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

        // Se le asigna al incidente un técnico de forma aleatoria
        List<Tecnico> listaTecnicos = tecnicoRepository.findAll();
        List<Tecnico> tecnicosDisponibles = new ArrayList<>(listaTecnicos.stream()
                .filter(Tecnico::isDisponibilidad).toList());
        Tecnico tecnico;
        if (!tecnicosDisponibles.isEmpty()) {
            Collections.shuffle(tecnicosDisponibles);
            tecnico = listaTecnicos.get(0);
            incident.setTecnico(tecnico);
        }else {
            throw new TecnicoNotFoundException("No se han encontrado técnicos disponibles", HttpStatus.NOT_FOUND);
        }
        incident.modificarEstado();
        incidenteRepository.save(incident);
        return new ResponseDto("Se ha asignado el técnico con éxito");
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
