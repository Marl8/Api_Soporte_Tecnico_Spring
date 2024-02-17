package com.utn.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @NotBlank(message = "Username es requerido")
    private String username;
    @NotBlank(message = "Password es requerido")
    private String password;
}
