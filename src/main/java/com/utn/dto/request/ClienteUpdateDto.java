package com.utn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteUpdateDto {

    @Positive(message = "Id debe ser un número positivo")
    private Long id;
    private String nombre;
    private String apellido;
    @NotBlank(message = "Razón Social es requerido")
    private String razonSocial;
    @NotBlank(message = "Teléfono es requerido")
    private String telefono;
    @NotBlank(message = "Cuit es requerido")
    private String cuit;
    @NotBlank(message = "Email es requerido")
    private String correoElectronico;
}
