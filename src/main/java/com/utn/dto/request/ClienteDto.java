package com.utn.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {

    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @NotBlank(message = "Razón Social es requerido")
    private String razonSocial;
    @NotBlank(message = "Teléfono es requerido")
    private String telefono;
    @NotBlank(message = "Cuit es requerido")
    @Pattern(regexp = "\\d{11}")
    private String cuit;
    @NotBlank(message = "Email es requerido")
    @Email(message = "Formato de email no reconocido")
    private String correoElectronico;
    private Set<Long> serviciosId;
}
