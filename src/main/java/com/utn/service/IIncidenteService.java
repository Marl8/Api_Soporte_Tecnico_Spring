package com.utn.service;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;

public interface IIncidenteService {

    ResponseIncidenteDto guardar(IncidenteDto incidenteDto);

    IncidenteDto findIncidente(Long id);

    ResponseIncidenteDto modificar(IncidenteDto incidenteDto);

    ResponseDto eliminar(Long id);
}
