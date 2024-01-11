package com.utn.service.Interfaces;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.dto.request.TecnicoUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseTecnicoDto;

import java.util.Set;

public interface ITecnicoService {

    ResponseTecnicoDto guardar(TecnicoDto tecnicoDto);

    TecnicoDto findTecnico(Long id);

    ResponseTecnicoDto modificar(TecnicoUpdateDto tecnicoDto);

    ResponseDto eliminar(Long id);

    Set<TecnicoFindDto> findAll();

    ResponseDto asignarEspecialidadATecnico(Long idEspecialidad, Long idTecnico);


    ResponseTecnicoDto tecnicoConMasResueltos(long dias);

    ResponseTecnicoDto tecnicoEspecialidadMasResueltos(long dias, String especialidad);
}
