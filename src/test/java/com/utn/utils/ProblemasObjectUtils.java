package com.utn.utils;

import com.utn.dto.request.TipoProblemaDto;
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

    public static TipoProblema problema2(){
        TipoProblema problema = new TipoProblema();
        problema.setDescripcion("Error al iniciar Tango");
        problema.setEspecialidad(EspecialidadObjectUtils.especialidad());
        problema.setTiempoEstimado(12);
        return problema;
    }

    public static TipoProblemaDto problemaDto(){
        TipoProblemaDto problema = new TipoProblemaDto();
        problema.setDescripcion("Error al iniciar Tango");
        problema.setEspecialidad(EspecialidadObjectUtils.especialidad());
        problema.setTiempoEstimado(12);
        return problema;
    }

    public static Set<TipoProblema> listaProblemas() {
        return Set.of(problema());
    }
}
