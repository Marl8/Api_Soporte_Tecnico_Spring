package com.utn.dto.request;

import com.utn.entity.Incidente;
import com.utn.entity.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperadorUpdateDto {

    private Long id;
    private String nombre;
    private String apellido;
    private Incidente incidente;
    private Set<Tecnico> listaTecnicosId;
}
