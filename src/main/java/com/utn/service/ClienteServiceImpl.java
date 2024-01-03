package com.utn.service;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.request.ClienteUpdateDto;
import com.utn.dto.response.ResponseClienteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.entity.Cliente;
import com.utn.entity.Servicio;
import com.utn.exception.ClienteNotFoundException;
import com.utn.exception.ServicioNotFoundException;
import com.utn.repository.ClienteRepository;
import com.utn.repository.ServicioRepository;
import com.utn.service.Interfaces.IClienteService;
import com.utn.utils.ClienteMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClienteServiceImpl implements IClienteService {

    ClienteRepository repository;

    ServicioRepository servicioRepository;

    ModelMapper mapper;

    public ClienteServiceImpl(ClienteRepository repository, ServicioRepository serv) {
        this.repository = repository;
        this.servicioRepository = serv;
        this.mapper = new ModelMapper();
    }

    @Override
    public ResponseClienteDto guardarCliente(ClienteDto clienteDto) {

        Optional<Cliente> existe = repository.findClienteByCuit(clienteDto.getCuit());
        if (existe.isPresent()){
            throw new ClienteNotFoundException("El cliente ya existe.", HttpStatus.BAD_REQUEST);
        }
        Cliente cliente = ClienteMapper.clienteSaveMapper(clienteDto);

        // Se le asignan los servicios contratados
        Set<Servicio> servicios = new HashSet<>();
        clienteDto.getServiciosId().forEach(serv -> {
            Servicio servicio = servicioRepository.findById(serv).orElseThrow(
                    () -> new ServicioNotFoundException("Servicio inexistente", HttpStatus.NOT_FOUND));
            servicios.add(servicio);
        });
        cliente.setServicios(servicios);
        repository.save(cliente);
        return new ResponseClienteDto(cliente.getRazonSocial(), cliente.getCuit(),
                "Cliente guardado con éxito.");
    }

    @Transactional
    @Override
    public ResponseDto asignarServicioACliente(Long idCliente, Long idServicio) {
        Cliente cliente = repository.findById(idCliente)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado", HttpStatus.NOT_FOUND));
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new ServicioNotFoundException("Servicio no encontrado", HttpStatus.NOT_FOUND));
        cliente.getServicios().add(servicio);
        ClienteUpdateDto clienteResponse = mapper.map(cliente, ClienteUpdateDto.class);
        this.modificar(clienteResponse);
        return new ResponseDto("Asignación realizada con éxito");
    }

    @Override
    public ClienteDto findCliente(Long id) {

        if(!repository.existsById(id)){
            throw new ClienteNotFoundException("No existen cliente con ese id.", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = repository.findById(id).get();
        return mapper.map(cliente, ClienteDto.class);
    }

    @Override
    public ResponseClienteDto modificar(ClienteUpdateDto clienteDto) {

        Cliente cliente = mapper.map(clienteDto, Cliente.class);
        Cliente encontrado = repository.findById(cliente.getId()).orElseThrow(
                () -> new ClienteNotFoundException("Cliente inexistente", HttpStatus.NOT_FOUND));
        encontrado.setNombre(cliente.getNombre());
        encontrado.setApellido(cliente.getApellido());
        encontrado.setRazonSocial(cliente.getRazonSocial());
        encontrado.setCuit(cliente.getCuit());
        encontrado.setTelefono(cliente.getTelefono());
        encontrado.setCorreoElectronico(cliente.getCorreoElectronico());
        encontrado.setIncidentes(cliente.getIncidentes());
        encontrado.setServicios(cliente.getServicios());

        repository.save(encontrado);
        return new ResponseClienteDto(encontrado.getRazonSocial(), encontrado.getCuit(),
                "Cliente modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Cliente> cliente = repository.findById(id);
        cliente.ifPresent(value -> repository.delete(value));
        return new ResponseDto("Cliente eliminado con éxito");
    }

}
