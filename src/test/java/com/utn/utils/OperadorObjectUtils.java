package com.utn.utils;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.request.OperadorUpdateDto;
import com.utn.entity.Operador;

import java.util.ArrayList;
import java.util.List;

public class OperadorObjectUtils {

    public static Operador operador(){
        Operador operador = new Operador();
        operador.setNombre("Luis");
        operador.setApellido("Sanchez");
        return operador;
    }

    public static Operador operador2(){
        Operador operador = new Operador();
        operador.setNombre("Carlos");
        operador.setApellido("Alonzo");
        return operador;
    }

    public static OperadorDto operadorDto(){
        OperadorDto operador = new OperadorDto();
        operador.setNombre("Luis");
        operador.setApellido("Sanchez");
        return operador;
    }

    public static OperadorUpdateDto operadorUpdateDto(){
        OperadorUpdateDto operador = new OperadorUpdateDto();
        operador.setNombre("Carlos");
        operador.setApellido("Alonzo");
        return operador;
    }

    public static List<Operador> listaOperadores(){
        return new ArrayList<>(List.of(operador(), operador2()));
    }
}
