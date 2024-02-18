package com.utn.dto.request;

import com.utn.entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCompleteDto {

    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @NotBlank(message = "Username es requerido")
    private String username;
    @NotBlank(message = "Password es requerido")
    private String password;
    @NotEmpty(message = "La lista no puede estar vacía")
    Set<Rol> roles;
}
