package com.utn.utils;

import com.utn.dto.request.TipoProblemaDto;
import com.utn.entity.TipoProblema;

public class TipoProblemaMapper {

    public static TipoProblema tipoProblemaSaveMapper(TipoProblemaDto tipoProblemaDto) {
        TipoProblema problema = new TipoProblema();
        problema.setDescripcion(tipoProblemaDto.getDescripcion());
        problema.setTiempoEstimado(tipoProblemaDto.getTiempoEstimado());
        return problema;
    }
}
