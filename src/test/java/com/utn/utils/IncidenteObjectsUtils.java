package com.utn.utils;

import com.utn.entity.EEstado;
import com.utn.entity.Incidente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncidenteObjectsUtils {

    public static Incidente incidente(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente());
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static Incidente incidente2(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente2());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico1());
        incidente.setFechaCreacion(LocalDateTime.of(2024, 1,30, 10,25));
        incidente.setFechaCierre(LocalDateTime.now());
        incidente.setEstado(EEstado.RESUELTO);
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static Incidente incidente3(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente2());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico2());
        incidente.setFechaCreacion(LocalDateTime.of(2024, 1,29, 6,17));
        incidente.setFechaCierre(LocalDateTime.now());
        incidente.setEstado(EEstado.RESUELTO);
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static Incidente incidente4(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico2());
        incidente.setFechaCreacion(LocalDateTime.of(2024, 1,31, 18,29));
        incidente.setFechaCierre(LocalDateTime.now());
        incidente.setEstado(EEstado.RESUELTO);
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static Incidente incidente5(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente2());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico2());
        incidente.setFechaCreacion(LocalDateTime.of(2024, 2,1, 11,9));
        incidente.setFechaCierre(LocalDateTime.now());
        incidente.setEstado(EEstado.RESUELTO);
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static Incidente incidente6(){
        Incidente incidente = new Incidente();
        incidente.setCliente(ClienteObjectUtils.cliente2());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico3());
        incidente.setFechaCreacion(LocalDateTime.of(2024, 2,2, 14,52));
        incidente.setFechaCierre(LocalDateTime.now());
        incidente.setEstado(EEstado.RESUELTO);
        incidente.setEsComplejo(false);
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static List<Incidente> listaIncidentes() {
        return new ArrayList<>(List.of(incidente2(), incidente3(), incidente4(), incidente5(), incidente6()));
    }
}
