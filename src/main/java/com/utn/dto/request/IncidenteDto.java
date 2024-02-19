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
public class IncidenteDto {

    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @Positive(message = "Debe ser un número positivo")
    private Long idServicio;
    @Positive(message = "Debe ser un número positivo")
    private Long idCliente;
    @NotEmpty(message = "No puede ser una lista vacía")
    private Set<Long> listaProblemas;
}
