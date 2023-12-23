package com.utn.dto.response;

import com.utn.dto.request.TipoProblemaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseProblemaDto {

    private TipoProblemaDto problema;
    private String mensaje;
}
