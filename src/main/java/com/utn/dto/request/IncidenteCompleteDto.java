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

    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @Positive(message = "Debe ser un número positivo")
    private int tiempoResolucion;
    @NotNull(message = "debe contener un estado")
    private EEstado estado;
    @NotNull(message = "No debe ser null")
    private boolean esComplejo;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCreacion;
    @NotNull(message = "No debe ser null")
    private LocalDateTime fechaCierre;
    @Positive(message = "Debe ser un número positivo")
    private int horaColchon;
    @NotEmpty(message = "No puede ser una lista vacía")
    private Set<TipoProblema> listaProblemas;
    @NotNull(message = "Debe contener un técnico")
    private Tecnico Tecnico;
    @NotNull(message = "Debe contener un cliente")
    private Cliente cliente;
    @NotNull(message = "Debe contener un servicio")
    private Servicio servicio;
    @NotNull(message = "Debe contener un operado")
    private Operador operador;
}
