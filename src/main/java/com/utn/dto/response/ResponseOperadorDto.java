package com.utn.dto.response;

import com.utn.dto.request.OperadorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseOperadorDto {

    private OperadorDto operadorDto;
    private String mensaje;
}
