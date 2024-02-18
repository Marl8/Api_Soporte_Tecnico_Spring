package com.utn.service.Interfaces;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;

public interface IIncidenteService {

    ResponseIncidenteDto guardar(IncidenteDto incidenteDto);
    IncidenteCompleteDto findIncidente(Long id);

    ResponseIncidenteDto modificar(IncidenteUpdateDto incidenteDto, Long id);

    ResponseDto eliminar(Long id);
}
