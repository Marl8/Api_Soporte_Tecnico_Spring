package com.utn.dto.response;

import com.utn.dto.request.ServicioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseServicioDto {

    private String tipoServicio;
    private String descripcion;
    private String mensaje;
}
