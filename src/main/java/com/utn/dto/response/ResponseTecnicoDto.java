package com.utn.dto.response;

import com.utn.dto.request.TecnicoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseTecnicoDto {

    private TecnicoDto tecnicoDto;
    private String mensaje;
}
