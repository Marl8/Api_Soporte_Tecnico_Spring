package com.utn.dto.request;

import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoProblemaCompleteDto {

    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @Positive(message = "Id debe ser un número positivo")
    private int tiempoEstimado;
    private Especialidad especialidad;
    private Incidente incidente;
}
