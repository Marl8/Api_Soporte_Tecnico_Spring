package com.utn.service;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.response.ResponseClienteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.entity.Cliente;
import com.utn.entity.Servicio;
import com.utn.repository.ClienteRepository;
import com.utn.repository.MesaAyudaRepository;
import com.utn.repository.ServicioRepository;
import com.utn.utils.ClienteMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClienteServiceImpl implements IClienteService{

    ClienteRepository repository;

    MesaAyudaRepository mesaRepository;

    ServicioRepository servicioRepository;

    ModelMapper mapper;

    public ClienteServiceImpl(ClienteRepository repository, ServicioRepository serv,
                              MesaAyudaRepository mesaRepository) {
        this.repository = repository;
        this.servicioRepository = serv;
        this.mesaRepository = mesaRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public ResponseClienteDto guardarCliente(ClienteDto clienteDto) {

        Optional<Cliente> existe = repository.findClienteByCuit(clienteDto.getCuit());
        if (existe.isPresent()){
            throw new RuntimeException("El cliente ya existe.");
        }
        Cliente cliente = ClienteMapper.clienteSaveMapper(clienteDto);

        // Se le asignan los servicios contratados
        Set<Servicio> servicios = new HashSet<>();
        clienteDto.getServiciosId().forEach(serv -> {
            Servicio servicio = servicioRepository.findById(serv).orElseThrow(
                    () -> new RuntimeException("Servicio inexistente"));
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
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        cliente.getServicios().add(servicio);
        ClienteDto clienteResponse = mapper.map(cliente, ClienteDto.class);
        this.modificar(clienteResponse);
        return new ResponseDto("Asignación realizada con éxito");
    }

    @Override
    public ClienteDto findCliente(Long id) {

        if(!repository.existsById(id)){
            throw new RuntimeException("No existen cliente con ese id.");
        }
        Cliente cliente = repository.findById(id).get();
        return mapper.map(cliente, ClienteDto.class);
    }

    @Override
    public ResponseClienteDto modificar(ClienteDto clienteDto) {

        Cliente cliente = mapper.map(clienteDto, Cliente.class);
        Cliente encontrado = repository.findById(cliente.getId()).orElseThrow(
                () -> new RuntimeException("Cliente inexistente"));
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
