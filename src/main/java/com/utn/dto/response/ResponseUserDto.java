package com.utn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUserDto {

    private String nombre;
    private String apellido;
    private String mensaje;
}
