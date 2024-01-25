package com.utn.utils;

import com.utn.entity.TipoProblema;

public class ProblemasObjectUtils {


    public static TipoProblema problema(){
        TipoProblema problema = new TipoProblema();
        problema.setDescripcion("Error al iniciar Tango");
        problema.setTiempoEstimado(12);
        return problema;
    }

}
