package com.utn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {

    private String nombre;
    private String apellido;
    private String razonSocial;
    private String telefono;
    private String cuit;
    private String correoElectronico;
    private Set<Long> serviciosId;
}
