package com.utn.dto.request;

import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import com.utn.entity.MedioNotificacionEnum;
import com.utn.entity.Operador;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TecnicoFindDto {

    @Positive(message = "Id debe ser un número positivo")
    private Long id;
    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @AssertTrue(message = "El técnico por default debe estar disponible")
    private boolean disponibilidad;
}
