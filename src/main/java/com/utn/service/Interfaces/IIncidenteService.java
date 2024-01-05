package com.utn.service.Interfaces;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;

public interface IIncidenteService {

    ResponseIncidenteDto guardar(IncidenteDto incidenteDto);
    IncidenteDto findIncidente(Long id);

    ResponseIncidenteDto modificar(IncidenteUpdateDto incidenteDto);

    ResponseDto eliminar(Long id);
}
