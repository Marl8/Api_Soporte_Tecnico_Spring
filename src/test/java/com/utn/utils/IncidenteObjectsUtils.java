package com.utn.utils;

import com.utn.entity.Incidente;

import java.time.LocalDateTime;

public class IncidenteObjectsUtils {

    public static Incidente incidente(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente());
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }
}
