package com.utn.utils;

import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteDto;
import com.utn.entity.Incidente;
import com.utn.entity.TipoProblema;

import java.util.stream.Collectors;

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

    public static IncidenteCompleteDto incidenteCompleteMapper(Incidente incidente){
        IncidenteCompleteDto i = new IncidenteCompleteDto();
        i.setDescripcion(incidente.getDescripcion());
        i.setTiempoResolucion(incidente.getTiempoResolucion());
        i.setHoraColchon(incidente.getHoraColchon());
        i.setEsComplejo(incidente.isEsComplejo());
        i.setFechaCierre(incidente.getFechaCierre());
        i.setEstado(incidente.getEstado());
        i.setIdcliente(incidente.getCliente().getId());
        i.setIdOperador(incidente.getOperador().getId());
        i.setIdServicio(incidente.getServicio().getId());
        i.setIdTecnico(incidente.getTecnico().getId());
        i.setListaProblemas(incidente.getListaProblemas().stream().map(TipoProblema::getId).collect(Collectors.toSet()));
        return i;
    }
}
