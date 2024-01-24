package com.utn.utils;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.request.ClienteUpdateDto;
import com.utn.entity.Cliente;

import java.util.HashSet;
import java.util.Set;

public class ClienteObjectUtils {

    public static Cliente cliente(){
        Cliente cliente = new Cliente();
        cliente.setRazonSocial("NN SA");
        cliente.setCuit("30254578094");
        cliente.setTelefono("0115786159");
        cliente.setCorreoElectronico("nn@Gmail.com");
        return cliente;
    }

    public static ClienteDto clienteDto(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setRazonSocial("NN SA");
        clienteDto.setCuit("30254578094");
        clienteDto.setTelefono("0115786159");
        clienteDto.setCorreoElectronico("nn@Gmail.com");
        Long id = 1L;
        Set<Long> idServicios = new HashSet<>();
        idServicios.add(id);
        clienteDto.setServiciosId(idServicios);
        return clienteDto;
    }

    public static ClienteUpdateDto clienteUpdateDto(){
        ClienteUpdateDto clienteDto = new ClienteUpdateDto();
        clienteDto.setRazonSocial("Suma SA");
        clienteDto.setCuit("30125869709");
        clienteDto.setTelefono("01145002981");
        clienteDto.setCorreoElectronico("suma@Gmail.com");
        return clienteDto;
    }

    public static Cliente clienteModificado(){
        Cliente cliente = new Cliente();
        cliente.setRazonSocial("Suma SA");
        cliente.setCuit("30125869709");
        cliente.setTelefono("01145002981");
        cliente.setCorreoElectronico("suma@Gmail.com");
        return cliente;
    }
}
