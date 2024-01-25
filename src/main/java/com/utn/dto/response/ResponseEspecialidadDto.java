package com.utn.dto.response;

import com.utn.dto.request.EspecialidadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseEspecialidadDto {

    private String nombre;
    private String descripcion;
    private String mensaje;
}

