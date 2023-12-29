package com.utn.service.Interfaces;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;

public interface IIncidenteService {

    ResponseIncidenteDto guardar(IncidenteDto incidenteDto);
    ResponseDto asignarHorasColchon(Long idIncidente, int horas);
    IncidenteDto findIncidente(Long id);

    ResponseIncidenteDto modificar(IncidenteDto incidenteDto);

    ResponseDto eliminar(Long id);
}
