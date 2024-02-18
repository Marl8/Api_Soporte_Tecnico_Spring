package com.utn.dto.request;

import com.utn.entity.Servicio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteFindDto {

    @NotNull(message = "Nombre es requerido")
    private String nombre;
    @NotNull(message = "Apellido es requerido")
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
    private Set<Servicio> servicios;
}
