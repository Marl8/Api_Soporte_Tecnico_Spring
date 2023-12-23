package com.utn.dto.request;

import com.utn.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicioDto {

    private Long id;
    private String tipoServicio;
    private String descripcion;
    private Set<Cliente> clientes;
}
