package com.utn.utils;

import com.utn.dto.request.TecnicoCompleteDto;
import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.entity.Especialidad;
import com.utn.entity.MedioNotificacionEnum;
import com.utn.entity.Tecnico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TecnicoObjectsUtils {

    public static Tecnico tecnico1(){
        Tecnico tecnico = new Tecnico();
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Juan");
        tecnico.setApellido("Sosa");
        tecnico.setListaEspecialidades(EspecialidadObjectUtils.listaSetEspecialidades());
        tecnico.setNotificacion(MedioNotificacionEnum.EMAIL);
        return tecnico;
    }

    public static Tecnico tecnico2(){
        Tecnico tecnico = new Tecnico();
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Diego");
        tecnico.setApellido("Palomino");
        tecnico.setListaEspecialidades(EspecialidadObjectUtils.listaSetEspecialidades());
        tecnico.setNotificacion(MedioNotificacionEnum.WHATSAPP);
        return tecnico;
    }

    public static Tecnico tecnico3(){
        Tecnico tecnico = new Tecnico();
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Ariel");
        tecnico.setApellido("Canale");
        tecnico.setListaEspecialidades(EspecialidadObjectUtils.listaSetEspecialidades());
        tecnico.setNotificacion(MedioNotificacionEnum.WHATSAPP);
        return tecnico;
    }

    public static Tecnico tecnico4(){
        Tecnico tecnico = new Tecnico();
        tecnico.setId(1L);
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Juan");
        tecnico.setApellido("Sosa");
        tecnico.setListaEspecialidades(EspecialidadObjectUtils.listaSetEspecialidades());
        tecnico.setNotificacion(MedioNotificacionEnum.EMAIL);
        return tecnico;
    }

    public static TecnicoDto tecnicoDto(){
        TecnicoDto tecnico = new TecnicoDto();
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Juan");
        tecnico.setApellido("Sosa");
        Set<Long> lista = new HashSet<>();
        lista.add(1L);
        tecnico.setListaEspecialidades(lista);
        tecnico.setNotificacion(MedioNotificacionEnum.EMAIL);
        return tecnico;
    }

    public static TecnicoCompleteDto tecnicoCompleteDto(){
        TecnicoCompleteDto tecnico = new TecnicoCompleteDto();
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Juan");
        tecnico.setApellido("Sosa");
        Set<Especialidad> lista = new HashSet<>();
        lista.add(EspecialidadObjectUtils.especialidad());
        tecnico.setListaEspecialidades(lista);
        tecnico.setNotificacion(MedioNotificacionEnum.EMAIL);
        return tecnico;
    }

    public static TecnicoFindDto tecnicoFindDto(){
        TecnicoFindDto tecnico = new TecnicoFindDto();
        tecnico.setNombre("Juan");
        tecnico.setApellido("Sosa");
        tecnico.setDisponibilidad(true);
        return tecnico;
    }

    public static TecnicoFindDto tecnicoFindDto2(){
        TecnicoFindDto tecnico = new TecnicoFindDto();
        tecnico.setNombre("Diego");
        tecnico.setApellido("Palomino");
        tecnico.setDisponibilidad(true);
        return tecnico;
    }

    public static TecnicoFindDto tecnicoFindDto3(){
        TecnicoFindDto tecnico = new TecnicoFindDto();
        tecnico.setDisponibilidad(true);
        tecnico.setNombre("Ariel");
        tecnico.setApellido("Canale");
        return tecnico;
    }

    public static Set<TecnicoFindDto> listaTecnicoFindDto(){
        return new HashSet<>(Set.of(tecnicoFindDto(), tecnicoFindDto2(), tecnicoFindDto3()));
    }

    public static List<Tecnico> listaTecnicos() {
        return new ArrayList<>(List.of(tecnico1(),tecnico2(), tecnico3()));
    }
}
