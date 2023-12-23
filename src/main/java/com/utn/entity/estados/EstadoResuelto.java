package com.utn.entity.estados;

import com.utn.entity.EEstado;
import com.utn.entity.Incidente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class EstadoResuelto implements IncidenciaEstado{

    private EEstado estado;

    @Override
    public Incidente cambiarEstado(Incidente incidente) {
        estado = EEstado.RESUELTO;
        return incidente;
    }

    @Override
    public void mostrarEstado() {
        System.out.println("El estado actual es:" + estado);
    }
}
