package com.utn.dto.request;

import com.utn.entity.Tecnico;
import com.utn.entity.TipoProblema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EspecialidadFindDto {

    private String nombre;
    private String descripcion;
    private Set<TipoProblema> listaProblemas;
    private Set<Tecnico> listaTecnicos;
}
