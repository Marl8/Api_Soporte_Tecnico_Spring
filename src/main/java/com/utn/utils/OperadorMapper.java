package com.utn.utils;

import com.utn.dto.request.OperadorDto;
import com.utn.entity.Operador;

public class OperadorMapper {

    public static Operador operador(OperadorDto operadorDto) {
        Operador operador = new Operador();
        operador.setNombre(operadorDto.getNombre());
        operador.setApellido(operadorDto.getApellido());
        return operador;
    }
}
