package com.utn.service;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseEspecialidadDto;
import com.utn.entity.Especialidad;
import com.utn.entity.Tecnico;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.TecnicoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EspecialidadServiceImpl implements IEspecialidadService {

    EspecialidadRepository repository;

    TecnicoRepository tecnicoRepository;

    public EspecialidadServiceImpl(EspecialidadRepository repository, TecnicoRepository tec) {
        this.repository = repository;
        this.tecnicoRepository = tec;
    }

    @Override
    public ResponseEspecialidadDto guardar(EspecialidadDto especialidadDto) {
        ModelMapper mapper = new ModelMapper();
        Especialidad especialidad = mapper.map(especialidadDto, Especialidad.class);

        if(verificarSiExiste(especialidad)){
            throw new RuntimeException("La especialidad ya existe.");
        }
        repository.save(especialidad);
        EspecialidadDto response = mapper.map(especialidad, EspecialidadDto.class);
        return new ResponseEspecialidadDto(response, "Especialidad guardada con éxito");
    }

    @Override
    public ResponseDto asignarEspecialidadATecnico(Long idEspecialidad, Long idTecnico) {
        Especialidad esp = repository.findById(idEspecialidad)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Tecnico tecnico = tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        esp.getListaTecnicos().add(tecnico);
        repository.save(esp);
        return new ResponseDto("Asignación realizada con éxito");
    }
    @Override
    public EspecialidadDto findEspecialidad(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen servicios con ese id.");
        }
        Especialidad esp = repository.findById(id).get();
        return mapper.map(esp, EspecialidadDto.class);
    }

    @Override
    public Set<EspecialidadDto> findAll() {
        ModelMapper mapper = new ModelMapper();
        List<Especialidad> especialidades = repository.findAll();
        Set<Especialidad> result = new HashSet<>(especialidades);
        return result.stream().map(e -> new EspecialidadDto(e.getId(),
                e.getDescripcion(), e.getNombre(), e.getListaProblemas(),
                e.getListaTecnicos())).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public ResponseEspecialidadDto modificar(EspecialidadDto especialidadDto) {
        ModelMapper mapper = new ModelMapper();
        Especialidad esp = mapper.map(especialidadDto, Especialidad.class);
        Optional<Especialidad> encontrado = repository.findById(esp.getId());

        if (encontrado.isPresent()) {
            Especialidad modificado = encontrado.get();
            modificado.setNombre(esp.getNombre());
            modificado.setDescripcion(esp.getDescripcion());
            modificado.setListaTecnicos(esp.getListaTecnicos());
            modificado.setListaProblemas(esp.getListaProblemas());
        }
        EspecialidadDto respuesta = mapper.map(esp, EspecialidadDto.class);
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
