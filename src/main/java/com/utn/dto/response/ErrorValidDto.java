package com.utn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorValidDto {

    private int status;
    private HashMap<String,String> errores;
}
