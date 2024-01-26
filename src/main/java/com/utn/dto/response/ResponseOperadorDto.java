package com.utn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseOperadorDto {

    private String nombre;
    private String apellido;
    private String mensaje;
}
