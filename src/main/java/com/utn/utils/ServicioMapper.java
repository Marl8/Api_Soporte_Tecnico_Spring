package com.utn.utils;

import com.utn.dto.request.ServicioDto;
import com.utn.entity.Servicio;

public class ServicioMapper {

    public static Servicio servicioSaveMapper(ServicioDto servicioDto){
        Servicio servicio = new Servicio();
        servicio.setTipoServicio(servicioDto.getTipoServicio());
        servicio.setDescripcion(servicioDto.getDescripcion());
        return servicio;
    }
}
