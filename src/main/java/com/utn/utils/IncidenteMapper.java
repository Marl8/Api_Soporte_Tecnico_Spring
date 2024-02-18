package com.utn.utils;

import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteDto;
import com.utn.entity.Incidente;

public class IncidenteMapper {

    public static Incidente incidenteSaveMapper(IncidenteDto incidentDto){
        Incidente incidente = new Incidente();
        incidente.setDescripcion(incidentDto.getDescripcion());
        return incidente;
    }

    public static Incidente incidenteUpdateMapper(IncidenteCompleteDto incidentDto){
        Incidente incidente = new Incidente();
        incidente.setDescripcion(incidentDto.getDescripcion());
        incidente.setTiempoResolucion(incidentDto.getTiempoResolucion());
        incidente.setHoraColchon(incidentDto.getHoraColchon());
        incidente.setEsComplejo(incidentDto.isEsComplejo());
        incidente.setFechaCierre(incidentDto.getFechaCierre());
        return incidente;
    }
}
