package com.utn.service;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseTecnicoDto;
import com.utn.entity.Especialidad;
import com.utn.entity.Tecnico;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.TecnicoRepository;
import com.utn.service.Interfaces.ITecnicoService;
import com.utn.utils.TecnicoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TecnicoServiceImpl implements ITecnicoService {

    TecnicoRepository repository;

    EspecialidadRepository especialidadRepository;

    public TecnicoServiceImpl(TecnicoRepository repository, EspecialidadRepository esp) {
        this.repository = repository;
        this.especialidadRepository = esp;
    }

    @Override
    public ResponseTecnicoDto guardar(TecnicoDto tecnicoDto) {
        ModelMapper mapper = new ModelMapper();
        Tecnico tecnico = TecnicoMapper.tecnicoSaveMapper(tecnicoDto);

        Optional<Tecnico> encontrado = repository.findTecnicoByNombreAndApellido(
                tecnico.getNombre(), tecnico.getApellido());

        if(encontrado.isPresent()) {
            throw new RuntimeException("Ya existe el técnico");
        }
        Set<Especialidad> listaEspecialidades = new HashSet<>();
        tecnicoDto.getListaEspecialidades().forEach(e -> {
            Especialidad esp = especialidadRepository.findById(e).orElseThrow(() ->
                    new RuntimeException("Especialidad Not found"));
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
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        Tecnico tecnico = repository.findById(idTecnico)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));
        tecnico.getListaEspecialidades().add(esp);
        repository.save(tecnico);
        return new ResponseDto("Asignación realizada con éxito");
    }
    @Override
    public TecnicoDto findTecnico(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen técnicos con ese id.");
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
    public ResponseTecnicoDto modificar(TecnicoDto tecnicoDto) {
        ModelMapper mapper = new ModelMapper();
        Tecnico tecnico = mapper.map(tecnicoDto, Tecnico.class);
        Tecnico encontrado = repository.findById(tecnico.getId())
                .orElseThrow(() -> new RuntimeException("Técnico Not found"));
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
}
