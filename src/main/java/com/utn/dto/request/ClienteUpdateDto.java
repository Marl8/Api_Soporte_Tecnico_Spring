package com.utn.dto.request;

import com.utn.entity.Incidente;
import com.utn.entity.Servicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteUpdateDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String razonSocial;
    private String telefono;
    private String cuit;
    private String correoElectronico;
    private Set<Incidente> incidentes;
    private Set<Servicio> servicios;
}
