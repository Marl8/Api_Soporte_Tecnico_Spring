package com.utn.utils;

import com.utn.dto.request.ServicioDto;
import com.utn.entity.Servicio;

import java.util.List;
import java.util.Set;

public class ServicioObjectUtils {

    public static Servicio servicio(){
        Servicio servicio = new Servicio();
        servicio.setTipoServicio("Servicio Técnico Windows");
        servicio.setDescripcion("Servicio para sistemas operativos de Microsoft");
        return servicio;
    }

    public static Servicio servicio2(){
        Servicio servicio = new Servicio();
        servicio.setTipoServicio("Servicio Técnico Mac");
        servicio.setDescripcion("Servicio para dispositivos con sistemas operativos de Apple");
        return servicio;
    }

    public static Servicio servicio3(){
        Servicio servicio = new Servicio();
        servicio.setId(1L);
        servicio.setTipoServicio("Servicio Técnico Windows");
        servicio.setDescripcion("Servicio para sistemas operativos de Microsoft");
        return servicio;
    }

    public static ServicioDto servicioDto(){
        ServicioDto servicio = new ServicioDto();
        servicio.setTipoServicio("Servicio Técnico Windows");
        servicio.setDescripcion("Servicio para sistemas operativos de Microsoft");
        return servicio;
    }

    public static ServicioDto servicioDto2(){
        ServicioDto servicio = new ServicioDto();
        servicio.setTipoServicio("Servicio Técnico Mac");
        servicio.setDescripcion("Servicio para dispositivos con sistemas operativos de Apple");
        return servicio;
    }

    public static List<Servicio> listaServicios() {
        return List.of(servicio(), servicio2());
    }

    public static Set<ServicioDto> listaSetServicios() {
        return Set.of(servicioDto(), servicioDto2());
    }
}
