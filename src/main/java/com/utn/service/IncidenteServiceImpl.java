package com.utn.service;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;
import com.utn.entity.Incidente;
import com.utn.entity.TipoProblema;
import com.utn.repository.IncidenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IncidenteServiceImpl implements IIncidenteService{

    IncidenteRepository repository;

    public IncidenteServiceImpl(IncidenteRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseIncidenteDto guardar(IncidenteDto incidenteDto) {
        ModelMapper mapper = new ModelMapper();
        Incidente incidente = mapper.map(incidenteDto, Incidente.class);

        if(verificarSiExiste(incidente)){
            throw new RuntimeException("El servicio ya existe.");
        }
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.modificarEstado();
        incidente.setTiempoResolucion(sumarTiempoEstimado(incidente.getListaProblemas()));
        repository.save(incidente);
        IncidenteDto response = mapper.map(incidente, IncidenteDto.class);
        return new ResponseIncidenteDto(response, "Incidente guardado con éxito.");
    }

    @Override
    public IncidenteDto findIncidente(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen incidentes con ese id.");
        }
        Incidente incidente = repository.findById(id).get();
        return mapper.map(incidente, IncidenteDto.class);
    }

    @Override
    public ResponseIncidenteDto modificar(IncidenteDto incidenteDto) {
        ModelMapper mapper = new ModelMapper();
        Incidente incidente = mapper.map(incidenteDto, Incidente.class);
        Incidente encontrado = repository.findById(incidente.getId())
                .orElseThrow(() -> new RuntimeException("No existen incidentes con este id"));

            encontrado.setCliente(incidente.getCliente());
            encontrado.setDescripcion(incidente.getDescripcion());
            encontrado.setEstado(incidente.getEstado());
            encontrado.setTecnico(incidente.getTecnico());
            encontrado.setOperador(incidente.getOperador());
            encontrado.setFechaCreacion(incidente.getFechaCreacion());
            encontrado.setFechaCierre(incidente.getFechaCierre());
            encontrado.setHoraColchon(incidente.getHoraColchon());
            encontrado.setListaProblemas(incidente.getListaProblemas());
            encontrado.setTiempoResolucion(incidente.getTiempoResolucion());
            encontrado.setServicio(incidente.getServicio());

            repository.save(encontrado);
        IncidenteDto respuesta = mapper.map(incidente, IncidenteDto.class);
        return new ResponseIncidenteDto(respuesta, "Incidente modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Incidente> incidente = repository.findById(id);

        if(incidente.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Servicio eliminado con éxito");
    }

    private boolean verificarSiExiste(Incidente incidente){

        List<Incidente> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Incidente> listaBusqueda = lista.stream()
                .filter(i -> i.getDescripcion().equals(incidente.getDescripcion())
                        && i.getCliente().equals(incidente.getCliente()) &&
                        i.getListaProblemas().equals(incidente.getListaProblemas()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }

    public int sumarTiempoEstimado(Set<TipoProblema> problemas) {
        return problemas.stream().mapToInt(TipoProblema::getTiempoEstimado).sum();
    }
}
