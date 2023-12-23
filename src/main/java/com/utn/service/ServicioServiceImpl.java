package com.utn.service;

import com.utn.dto.request.ServicioDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseServicioDto;
import com.utn.entity.Cliente;
import com.utn.entity.Servicio;
import com.utn.repository.ClienteRepository;
import com.utn.repository.ServicioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements IServicioService{

    ServicioRepository repository;

    ClienteRepository clienteRepository;

    public ServicioServiceImpl(ServicioRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
    }

    @Override
    public ResponseServicioDto guardarServicio(ServicioDto servicioDto) {
        ModelMapper mapper = new ModelMapper();
        Servicio servicio = mapper.map(servicioDto, Servicio.class);

        if(verificarSiExiste(servicio)){
            throw new RuntimeException("El servicio ya existe.");
        }
        repository.save(servicio);
        ServicioDto response = mapper.map(servicio, ServicioDto.class);
        return new ResponseServicioDto(response, "Servicio guardado con éxito.");
    }

    @Override
    public ResponseDto asignarServicioACliente(Long idCliente, Long idServicio) {
        ModelMapper mapper = new ModelMapper();
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Servicio servicio = repository.findById(idServicio)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.getClientes().add(cliente);
        ServicioDto clienteResponse = mapper.map(servicio, ServicioDto.class);
        this.modificar(clienteResponse);
        return new ResponseDto("Asignación realizada con éxito");
    }
    @Override
    public ServicioDto findServicio(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen servicios con ese id.");
        }
        Servicio servicio = repository.findById(id).get();
        return mapper.map(servicio, ServicioDto.class);
    }

    @Override
    public Set<ServicioDto> findAll() {
        ModelMapper mapper = new ModelMapper();
        List<Servicio> servicios = repository.findAll();
        Set<Servicio> result = new HashSet<>(servicios);
        return result.stream().map(s -> new ServicioDto(s.getId(),
                s.getDescripcion(), s.getTipoServicio(),
                s.getClientes())).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public ResponseServicioDto modificar(ServicioDto servicioDto) {
        ModelMapper mapper = new ModelMapper();
        Servicio servicio = mapper.map(servicioDto, Servicio.class);
        Optional<Servicio> encontrado = repository.findById(servicio.getId());

        if (encontrado.isPresent()) {
            Servicio modificado = encontrado.get();
            modificado.setTipoServicio(servicio.getTipoServicio());
            modificado.setDescripcion(servicio.getDescripcion());
            modificado.setClientes(servicio.getClientes());
        }
        ServicioDto respuesta = mapper.map(servicio, ServicioDto.class);
        return new ResponseServicioDto(respuesta, "Servicio modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Servicio> servicio = repository.findById(id);

        if(servicio.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Servicio eliminado con éxito");
    }

    private boolean verificarSiExiste(Servicio servicio){

        List<Servicio> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Servicio> listaBusqueda = lista.stream()
                .filter(s -> s.getDescripcion().equals(servicio.getDescripcion())
                        && s.getTipoServicio().equals(servicio.getTipoServicio()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }
}