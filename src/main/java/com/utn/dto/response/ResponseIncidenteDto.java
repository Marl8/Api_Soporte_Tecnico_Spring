package com.utn.dto.response;

import com.utn.dto.request.IncidenteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseIncidenteDto {

    private IncidenteDto incidenteDto;
    private String mensaje;
}

