package com.utn.dto.request;

import com.utn.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncidenteUpdateDto {

    @Positive(message = "Id debe ser un número positivo")
    private Long id;
    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @NotNull(message = "No debe ser null")
    EEstado estado;
    @Positive(message = "Debe ser un número positivo")
    private int tiempoResolucion;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCreacion;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCierre;
    @Positive(message = "Debe ser un número positivo")
    private int horaColchon;
    @Positive(message = "Debe ser un número positivo")
    private Servicio idServicio;
    @Positive(message = "Debe ser un número positivo")
    private Tecnico idTecnico;
    @Positive(message = "Debe ser un número positivo")
    private Cliente idCliente;
    @NotEmpty(message = "No puede ser una lista vacía")
    private Set<TipoProblema> listaProblemas;
}
