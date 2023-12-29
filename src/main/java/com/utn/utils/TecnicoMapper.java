package com.utn.utils;

import com.utn.dto.request.TecnicoDto;
import com.utn.entity.Tecnico;

public class TecnicoMapper {

    public static Tecnico tecnicoSaveMapper(TecnicoDto tecnicoDto){
        Tecnico tecnico = new Tecnico();
        tecnico.setDisponibilidad(tecnicoDto.isDisponibilidad());
        tecnico.setApellido(tecnicoDto.getApellido());
        tecnico.setNombre(tecnicoDto.getNombre());
        tecnico.setNotificacion(tecnicoDto.getNotificacion());
        return tecnico;
    }

}
