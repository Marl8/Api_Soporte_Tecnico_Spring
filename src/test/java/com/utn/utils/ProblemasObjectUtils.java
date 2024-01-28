package com.utn.utils;

import com.utn.entity.TipoProblema;

import java.util.Set;

public class ProblemasObjectUtils {


    public static TipoProblema problema(){
        TipoProblema problema = new TipoProblema();
        problema.setDescripcion("Error al iniciar Tango");
        problema.setEspecialidad(EspecialidadObjectUtils.especialidad());
        problema.setTiempoEstimado(12);
        return problema;
    }

    public static Set<TipoProblema> listaProblemas() {
        return Set.of(problema());
    }
}
