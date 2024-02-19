package com.utn.service.Interfaces;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.request.ClienteFindDto;
import com.utn.dto.request.ClienteUpdateDto;
import com.utn.dto.response.ResponseClienteDto;
import com.utn.dto.response.ResponseDto;

public interface IClienteService {
    ResponseClienteDto guardarCliente(ClienteDto clienteDto);

    ResponseDto asignarServicioACliente(Long idCliente, Long idServicio);

    ClienteFindDto findCliente(Long id);

    ResponseClienteDto modificar(ClienteUpdateDto clienteDto, Long id);

    ResponseDto eliminar(Long id);
}
