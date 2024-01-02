package com.utn.dto.request;

import com.utn.entity.Incidente;
import com.utn.entity.Servicio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteUpdateDto {

    @Positive(message = "Id debe ser un número positivo")
    private Long id;
    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @NotBlank(message = "Razón Social es requerido")
    private String razonSocial;
    @NotBlank(message = "Teléfono es requerido")
    private String telefono;
    @NotBlank(message = "Cuit es requerido")
    private String cuit;
    @NotBlank(message = "Email es requerido")
    private String correoElectronico;
    private Set<Incidente> incidentes;
    private Set<Servicio> servicios;
}
