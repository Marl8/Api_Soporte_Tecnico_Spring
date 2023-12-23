package com.utn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseClienteDto {

    private String razonSocial;
    private String cuit;
    private String mensaje;
}
