package com.utn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EspecialidadDto {

    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Descripción es requerido")
    private String descripcion;
    @NotEmpty(message = "Lista no debe estar vacía")
    private Set<Long> listaProblemasId;
}
