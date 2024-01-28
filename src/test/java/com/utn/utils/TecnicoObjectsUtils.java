package com.utn.utils;

import com.utn.entity.MedioNotificacionEnum;
import com.utn.entity.Tecnico;

import java.util.List;

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

    public static List<Tecnico> listaTecnicos() {
        return List.of(tecnico1(),tecnico2());
    }
}
