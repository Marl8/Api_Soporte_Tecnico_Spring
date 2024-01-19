package com.utn.utils;

import com.utn.entity.Servicio;

public class ServicioObjectUtils {

    public static Servicio servicio(){
        Servicio servicio = new Servicio();
        servicio.setTipoServicio("Servicio Técnico Windows");
        servicio.setDescripcion("Servicio sistemas operativos de Microsoft");
        return servicio;
    }
}
