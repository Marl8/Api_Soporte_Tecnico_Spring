package com.utn.utils;

import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.entity.EEstado;
import com.utn.entity.Incidente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncidenteObjectsUtils {

    public static Incidente incidente(){
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Problemas con el inicio de sesión en Windows 11");
        incidente.setCliente(ClienteObjectUtils.cliente());
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.setEsComplejo(false);
        incidente.setOperador(OperadorObjectUtils.operador3());
        incidente.setServicio(ServicioObjectUtils.servicio3());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico4());
        incidente.setCliente(ClienteObjectUtils.cliente3());
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static Incidente incidente2(){
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Problemas con la instalación en Linux");
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

    public static Incidente incidente7(){
        Incidente incidente = new Incidente();
        incidente.setDescripcion("Problemas con el inicio de sesión en Windows 11");
        incidente.setCliente(ClienteObjectUtils.cliente());
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.setFechaCierre(LocalDateTime.now());
        incidente.setEsComplejo(false);
        incidente.setOperador(OperadorObjectUtils.operador3());
        incidente.setServicio(ServicioObjectUtils.servicio3());
        incidente.setTecnico(TecnicoObjectsUtils.tecnico4());
        incidente.setCliente(ClienteObjectUtils.cliente3());
        incidente.setListaProblemas(ProblemasObjectUtils.listaProblemas());
        return incidente;
    }

    public static IncidenteDto incidenteDto(){
        IncidenteDto incidente = new IncidenteDto();
        incidente.setDescripcion("Problemas con el inicio de sesión en Windows 11");
        incidente.setIdCliente(1L);
        incidente.setIdServicio(1L);
        Set<Long> listaIdProblemas = new HashSet<>();
        listaIdProblemas.add(1L);
        incidente.setListaProblemas(listaIdProblemas);
        return incidente;
    }

    public static IncidenteUpdateDto incidenteUpdateDto(){
        IncidenteUpdateDto incidente = new IncidenteUpdateDto();
        incidente.setDescripcion("Problemas con el inicio de sesión en Windows 11");
        return incidente;
    }

    public static IncidenteUpdateDto incidenteUpdateDto2(){
        IncidenteUpdateDto incidente = new IncidenteUpdateDto();
        incidente.setDescripcion("Problemas con la instalación en Linux");
        incidente.setListaProblemas(Set.of(1L));
        return incidente;
    }

    public static IncidenteCompleteDto incidenteCompleteDto(){
        IncidenteCompleteDto incidente = new IncidenteCompleteDto();
        incidente.setDescripcion("Problemas con el inicio de sesión en Windows 11");
        incidente.setIdcliente(1L);
        incidente.setIdOperador(1L);
        incidente.setIdServicio(1L);
        incidente.setIdTecnico(1L);
        Set<Long> listaProblemas = new HashSet<>();
        Long id = 1L;
        listaProblemas.add(id);
        incidente.setListaProblemas(listaProblemas);
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.setEsComplejo(false);
        return incidente;
    }

    public static List<Incidente> listaIncidentes() {
        return new ArrayList<>(List.of(incidente2(), incidente3(), incidente4(), incidente5(), incidente6()));
    }
}
