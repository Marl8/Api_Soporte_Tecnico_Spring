package com.utn.dto.request;

import com.utn.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncidenteDto {

    private Long id;
    private String descripcion;
    EEstado estado;
    private int tiempoResolucion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;
    private int horaColchon;
    private Servicio servicio;
    private Tecnico tecnico;
    private Cliente cliente;
    private Set<TipoProblema> listaProblemas;
}
