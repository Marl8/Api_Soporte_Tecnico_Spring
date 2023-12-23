package com.utn.service;

import com.utn.dto.request.ServicioDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseServicioDto;

import java.util.Set;

public interface IServicioService {

    ResponseServicioDto guardarServicio(ServicioDto servicioDto);

    ServicioDto findServicio(Long id);

    ResponseServicioDto modificar(ServicioDto servicioDto);

    ResponseDto eliminar(Long id);

    Set<ServicioDto> findAll();

    ResponseDto asignarServicioACliente(Long idCliente, Long idServicio);
}

