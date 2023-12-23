package com.utn.service;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.response.ResponseClienteDto;
import com.utn.dto.response.ResponseDto;

public interface IClienteService {
    ResponseClienteDto guardarCliente(ClienteDto clienteDto);

    ResponseDto asignarServicioACliente(Long idCliente, Long idServicio);

    ClienteDto findCliente(Long id);

    ResponseClienteDto modificar(ClienteDto clienteDto);

    ResponseDto eliminar(Long id);
}
