package com.utn.service;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;
import com.utn.entity.*;
import com.utn.exception.*;
import com.utn.repository.*;
import com.utn.service.Interfaces.IIncidenteService;
import com.utn.utils.IncidenteMapper;
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

    TecnicoRepository tecnicoRepository;

    public IncidenteServiceImpl(IncidenteRepository repository, ClienteRepository clienteRepository,
                                ServicioRepository servicioRepository, ProblemaRepository problema,
                                TecnicoRepository tecnicoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.problemaRepository = problema;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public ResponseIncidenteDto guardar(IncidenteDto incidenteDto) {
        Incidente incidente = IncidenteMapper.incidenteSaveMapper(incidenteDto);

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
        problemas.forEach(p -> {
            p.setIncidente(incidente);
        });
        incidente.setCliente(cliente);
        incidente.setServicio(servicio);
        incidente.setListaProblemas(problemas);
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.modificarEstado();
        incidente.setTiempoResolucion(sumarTiempoEstimado(incidente.getListaProblemas()));
        Incidente guardado = repository.save(incidente);
        return new ResponseIncidenteDto(guardado.getDescripcion(), "Incidente guardado con éxito.");
    }

    @Override
    public IncidenteCompleteDto findIncidente(Long id) {

        if(!repository.existsById(id)){
            throw new IncidenteNotFoundException("No existen incidentes con ese id.", HttpStatus.NOT_FOUND);
        }
        Incidente incidente = repository.findById(id).get();
        return IncidenteMapper.incidenteCompleteMapper(incidente);
    }

    @Override
    public ResponseIncidenteDto modificar(IncidenteUpdateDto incidenteDto, Long id) {
        ModelMapper mapper = new ModelMapper();
        Incidente incidente = mapper.map(incidenteDto, Incidente.class);
        Incidente encontrado = repository.findById(id)
                .orElseThrow(() -> new IncidenteNotFoundException("No existen incidentes con ese id.", HttpStatus.NOT_FOUND));
        Tecnico tecnico = tecnicoRepository.findById(incidenteDto.getIdTecnico()).orElseThrow(
                () -> new TecnicoNotFoundException("Técnico no encontrado", HttpStatus.BAD_REQUEST)
        );
        Set<TipoProblema> listaProblemas = new HashSet<>();
        incidenteDto.getListaProblemas().forEach(e -> {
            TipoProblema p = problemaRepository.findById(e).orElseThrow(() ->
                    new EspecialidadNotFoundException("Especialidad Not found", HttpStatus.NOT_FOUND));
            listaProblemas.add(p);
        });
        encontrado.setListaProblemas(listaProblemas);
        encontrado.setDescripcion(incidente.getDescripcion());
        encontrado.setFechaCierre(incidente.getFechaCierre());
        encontrado.setEsComplejo(incidente.isEsComplejo());
        encontrado.setHoraColchon(incidente.getHoraColchon());
        encontrado.setListaProblemas(incidente.getListaProblemas());
        encontrado.setTiempoResolucion(incidente.getTiempoResolucion());
        encontrado.setTecnico(tecnico);

        if(encontrado.getFechaCierre() != null){
            encontrado.modificarEstado();
            encontrado.getTecnico().setDisponibilidad(true);
        }
        Incidente modificado = repository.save(encontrado);
        return new ResponseIncidenteDto(modificado.getDescripcion(), "Incidente modificado con éxito");
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
