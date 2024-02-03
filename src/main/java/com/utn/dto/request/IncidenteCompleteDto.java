package com.utn.dto.request;

import com.utn.entity.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncidenteCompleteDto {

    @Positive(message = "Id debe ser un número positivo")
    private Long id;
    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @NotNull(message = "No debe ser null")
    EEstado estado;
    @Positive(message = "Debe ser un número positivo")
    private int tiempoResolucion;
    @NotNull(message = "No debe ser null")
    private boolean esComplejo;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCreacion;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCierre;
    @Positive(message = "Debe ser un número positivo")
    private int horaColchon;
    @Positive(message = "Debe ser un número positivo")
    private Servicio servicio;
    @Positive(message = "Debe ser un número positivo")
    private Tecnico tecnico;
    @Positive(message = "Debe ser un número positivo")
    private Cliente cliente;
    @NotEmpty(message = "No puede ser una lista vacía")
    private Set<TipoProblema> listaProblemas;
}
