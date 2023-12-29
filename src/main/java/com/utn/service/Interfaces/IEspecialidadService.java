package com.utn.service.Interfaces;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.request.EspecialidadFindDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseEspecialidadDto;

import java.util.Set;

public interface IEspecialidadService {

    ResponseEspecialidadDto guardar(EspecialidadDto especialidadDto);

    EspecialidadFindDto findEspecialidad(Long id);

    ResponseEspecialidadDto modificar(EspecialidadDto especialidadDto);

    ResponseDto eliminar(Long id);

    Set<EspecialidadFindDto> findAll();
    ResponseDto asignarProblema(Long idEspecialidad, Long idTProblema);
}
