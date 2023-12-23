package com.utn.entity.estados;

import com.utn.entity.Incidente;

public interface IncidenciaEstado {
    void mostrarEstado();
    Incidente cambiarEstado(Incidente incidente);
}
