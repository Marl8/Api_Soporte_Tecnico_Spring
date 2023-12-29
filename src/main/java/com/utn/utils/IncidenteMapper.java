package com.utn.utils;

import com.utn.dto.request.IncidenteDto;
import com.utn.entity.Incidente;

public class IncidenteMapper {

    public static Incidente incidenteSaveMapper(IncidenteDto incidentDto){
        Incidente incidente = new Incidente();
        incidente.setDescripcion(incidentDto.getDescripcion());
        return incidente;
    }
}
