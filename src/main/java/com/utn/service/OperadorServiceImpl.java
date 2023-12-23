package com.utn.service;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseOperadorDto;
import com.utn.entity.Incidente;
import com.utn.entity.Operador;
import com.utn.entity.Tecnico;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.OperadorRepository;
import com.utn.repository.TecnicoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OperadorServiceImpl implements IOperadorService{

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
            throw new RuntimeException("El operador ya existe.");
        }
        repository.save(operador);
        OperadorDto response = mapper.map(operador, OperadorDto.class);
        return new ResponseOperadorDto(response, "Operador guardado con éxito.");
    }

    @Override
    public OperadorDto findOperador(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen operadores con ese id.");
        }
        Operador operador = repository.findById(id).get();
        return mapper.map(operador, OperadorDto.class);
    }

    @Override
    public ResponseOperadorDto modificar(OperadorDto operadorDto) {
        ModelMapper mapper = new ModelMapper();
        Operador operador = mapper.map(operadorDto, Operador.class);
        Operador encontrado = repository.findById(operador.getId())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el operador"));

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

    @Transactional
    @Override
    public ResponseDto asignarTecnico(Long idIncidente) {
        Incidente incident = incidenteRepository.findById(idIncidente)
                .orElseThrow(() -> new RuntimeException("Incidente not found"));
        List<Operador> listaOperadores = repository.findAll();
        Operador operador;

        // Se asigna un operador de forma aleatoria
        if (!listaOperadores.isEmpty()) {
            Collections.shuffle(listaOperadores);
            operador = listaOperadores.get(0);
            incident.setOperador(operador);
        }else {
            throw new RuntimeException("No se han encontrado operadores");
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
            throw new RuntimeException("No se han encontrado operadores");
        }
        incident.modificarEstado();
        incidenteRepository.save(incident);
        return new ResponseDto("Se ha asignado el técnico con éxito");
    }

    @Override
    public ResponseDto asignarListaTecnico(Long idOperador) {
        Operador operador = repository.findById(idOperador)
                .orElseThrow(() -> new RuntimeException("Operador Not found"));
        List<Tecnico> listaTecnicos = tecnicoRepository.findAll();
        Set<Tecnico> result = new HashSet<>(listaTecnicos);
        if(!listaTecnicos.isEmpty()){
            operador.setListaTecnicos(result);
            repository.save(operador);
        }else{
            throw new RuntimeException("No se han encontrado operadores");
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
