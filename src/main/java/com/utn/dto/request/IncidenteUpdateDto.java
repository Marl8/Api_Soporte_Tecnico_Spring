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
    @NotEmpty(message = "No puede ser una lista vacía")
    private Set<Long> listaProblemas;
    @NotNull(message = "Debe contener un id de técnico")
    private Long idTecnico;
}
