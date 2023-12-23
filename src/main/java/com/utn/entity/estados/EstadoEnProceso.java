package com.utn.entity.estados;

import com.utn.entity.EEstado;
import com.utn.entity.Incidente;

public class EstadoEnProceso implements IncidenciaEstado {

    private EEstado estado;

    @Override
    public void mostrarEstado() {
        System.out.println("El estado actual es:" + estado);
    }

    @Override
    public Incidente cambiarEstado(Incidente incidente) {
        incidente.setEstado(EEstado.RESUELTO);
        estado = EEstado.RESUELTO;
        return incidente;
    }
}