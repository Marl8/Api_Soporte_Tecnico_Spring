package com.utn.service;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseTecnicoDto;
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
public class TecnicoServiceImpl implements ITecnicoService{

    TecnicoRepository repository;

    EspecialidadRepository especialidadRepository;

    public TecnicoServiceImpl(TecnicoRepository repository, EspecialidadRepository esp) {
        this.repository = repository;
        this.especialidadRepository = esp;
    }

    @Override
    public ResponseTecnicoDto guardar(TecnicoDto tecnicoDto) {
        ModelMapper mapper = new ModelMapper();
        Tecnico tecnico = mapper.map(tecnicoDto, Tecnico.class);

        if(verificarSiExiste(tecnico)){
            throw new RuntimeException("La especialidad ya existe.");
        }
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
        Optional<Tecnico> encontrado = repository.findById(tecnico.getId());

        if (encontrado.isPresent()) {
            Tecnico modificado = encontrado.get();
            modificado.setNombre(tecnico.getNombre());
            modificado.setApellido(tecnico.getApellido());
            modificado.setDisponibilidad(tecnico.isDisponibilidad());
            modificado.setIncidentes(tecnico.getIncidentes());
            modificado.setListaEspecialidades(tecnico.getListaEspecialidades());
            modificado.setNotificacion(tecnico.getNotificacion());
        }
        TecnicoDto respuesta = mapper.map(tecnico, TecnicoDto.class);
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


    private boolean verificarSiExiste(Tecnico tecnico){

        List<Tecnico> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Tecnico> listaBusqueda = lista.stream()
                .filter(t -> t.getNombre().equals(tecnico.getNombre())
                        && t.getApellido().equals(t.getApellido()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }
}
