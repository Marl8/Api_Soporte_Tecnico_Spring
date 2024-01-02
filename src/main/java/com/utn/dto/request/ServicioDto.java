package com.utn.dto.request;

import com.utn.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicioDto {

    @Positive(message = "Id debe ser un número positivo")
    private Long id;
    @NotBlank(message = "Tipo de Servicioes requerido")
    private String tipoServicio;
    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
}
