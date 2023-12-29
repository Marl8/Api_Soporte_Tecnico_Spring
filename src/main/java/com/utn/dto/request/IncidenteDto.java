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
    private Long idServicio;
    private Long idTecnico;
    private Long idCliente;
    private Set<Long> listaProblemas;
}
