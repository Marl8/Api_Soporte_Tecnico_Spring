package com.utn.service;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;
import com.utn.entity.Cliente;
import com.utn.entity.Incidente;
import com.utn.entity.Servicio;
import com.utn.entity.TipoProblema;
import com.utn.exception.ClienteNotFoundException;
import com.utn.exception.IncidenteNotFoundException;
import com.utn.exception.ProblemaNotFoundException;
import com.utn.exception.ServicioNotFoundException;
import com.utn.repository.ClienteRepository;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.ProblemaRepository;
import com.utn.repository.ServicioRepository;
import com.utn.service.Interfaces.IIncidenteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IncidenteServiceImpl implements IIncidenteService {

    IncidenteRepository repository;

    ClienteRepository clienteRepository;

    ServicioRepository servicioRepository;

    ProblemaRepository problemaRepository;

    public IncidenteServiceImpl(IncidenteRepository repository, ClienteRepository clienteRepository,
                                ServicioRepository servicioRepository, ProblemaRepository problema) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.problemaRepository = problema;
    }

    @Override
    public ResponseIncidenteDto guardar(IncidenteDto incidenteDto) {
        ModelMapper mapper = new ModelMapper();
        Incidente incidente = mapper.map(incidenteDto, Incidente.class);

        if(verificarSiExiste(incidente)){
            throw new RuntimeException("El servicio ya existe.");
        }
        Cliente cliente = clienteRepository.findById(incidenteDto.getIdCliente()).orElseThrow(
                () -> new ClienteNotFoundException("Cliente not found", HttpStatus.NOT_FOUND));
        Servicio servicio = servicioRepository.findById(incidenteDto.getIdServicio()).orElseThrow(
                () -> new ServicioNotFoundException("Service not found", HttpStatus.NOT_FOUND));
        Set<TipoProblema> problemas = new HashSet<>();
        incidenteDto.getListaProblemas().forEach(p -> {
            TipoProblema problema = problemaRepository.findById(p).orElseThrow(
                    () -> new ProblemaNotFoundException("Problema Not found", HttpStatus.NOT_FOUND));
            problemas.add(problema);
            });
        incidente.setCliente(cliente);
        incidente.setServicio(servicio);
        incidente.setListaProblemas(problemas);
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
            throw new IncidenteNotFoundException("No existen incidentes con ese id.", HttpStatus.NOT_FOUND);
        }
        Incidente incidente = repository.findById(id).get();
        return mapper.map(incidente, IncidenteDto.class);
    }

    @Override
    public ResponseIncidenteDto modificar(IncidenteUpdateDto incidenteDto) {
        ModelMapper mapper = new ModelMapper();
        Incidente incidente = mapper.map(incidenteDto, Incidente.class);
        Incidente encontrado = repository.findById(incidente.getId())
                .orElseThrow(() -> new IncidenteNotFoundException("No existen incidentes con ese id.", HttpStatus.NOT_FOUND));

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

        if(incidente.getFechaCierre() != null){
            incidente.modificarEstado();
            incidente.getTecnico().setDisponibilidad(true);
        }
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
