package com.utn.entity.estados;

import com.utn.entity.EEstado;
import com.utn.entity.Incidente;

public class EstadoPendiente implements IncidenciaEstado {

    private EEstado estado;

    @Override
    public Incidente cambiarEstado(Incidente incidente) {
        incidente.setEstado(EEstado.EN_PROCESO);
        estado = EEstado.EN_PROCESO;
        return incidente;
    }

    @Override
    public void mostrarEstado() {
        System.out.println("El estado actual es:" + estado);
    }
}
