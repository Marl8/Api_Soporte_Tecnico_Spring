package com.utn.dto.request;

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

    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @Positive(message = "Debe ser un número positivo")
    private int tiempoResolucion;
    @NotNull(message = "No debe ser null")
    private boolean esComplejo;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCierre;
    @Positive(message = "Debe ser un número positivo")
    private int horaColchon;
    @NotEmpty(message = "No puede ser una lista vacía")
    private Set<Long> listaProblemas;
    @NotNull(message = "Debe contener un id de técnico")
    private Long idTecnico;
}
