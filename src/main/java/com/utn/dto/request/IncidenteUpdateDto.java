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
public class IncidenteUpdateDto {

    private Long id;
    private String descripcion;
    EEstado estado;
    private int tiempoResolucion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;
    private int horaColchon;
    private Servicio idServicio;
    private Tecnico idTecnico;
    private Cliente idCliente;
    private Set<TipoProblema> listaProblemas;
}
