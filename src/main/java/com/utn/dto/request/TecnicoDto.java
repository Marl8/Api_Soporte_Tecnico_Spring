package com.utn.dto.request;

import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import com.utn.entity.MedioNotificacionEnum;
import com.utn.entity.Operador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TecnicoDto {

    private Long id;
    private String nombre;
    private String apellido;
    private boolean disponibilidad;
    private MedioNotificacionEnum notificacion;
    private Set<Long> listaEspecialidades;
}
