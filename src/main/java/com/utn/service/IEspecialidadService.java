package com.utn.service;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseEspecialidadDto;

import java.util.Set;

public interface IEspecialidadService {

    ResponseEspecialidadDto guardar(EspecialidadDto especialidadDto);

    EspecialidadDto findEspecialidad(Long id);

    ResponseEspecialidadDto modificar(EspecialidadDto especialidadDto);

    ResponseDto eliminar(Long id);

    Set<EspecialidadDto> findAll();

    ResponseDto asignarEspecialidadATecnico(Long idEspecialidad, Long idTecnico);
}
