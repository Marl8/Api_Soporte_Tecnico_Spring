package com.utn.utils;

import com.utn.dto.request.ClienteDto;
import com.utn.entity.Cliente;

public class ClienteMapper {

    public static Cliente clienteSaveMapper(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setCuit(clienteDto.getCuit());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setRazonSocial(clienteDto.getRazonSocial());
        cliente.setCorreoElectronico(clienteDto.getCorreoElectronico());
        cliente.setTelefono(clienteDto.getTelefono());
        return cliente;
    }
}
