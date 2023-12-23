package com.utn.dto.request;

import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoProblemaDto {

    private Long id;

    private String descripcion;

    private int tiempoEstimado;

    private Incidente incidente;

    private Especialidad especialidad;
}
