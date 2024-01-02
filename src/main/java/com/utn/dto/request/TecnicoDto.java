package com.utn.dto.request;

import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import com.utn.entity.MedioNotificacionEnum;
import com.utn.entity.Operador;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TecnicoDto {

    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @AssertTrue(message = "El técnico por default debe estar disponible")
    private boolean disponibilidad;
    @NotNull(message = "Debe tener un medio de notificación")
    private MedioNotificacionEnum notificacion;
    @NotNull(message = "La lista no puede estar vacía")
    private Set<Long> listaEspecialidades;
}
