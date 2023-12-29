package com.utn.utils;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.request.EspecialidadFindDto;
import com.utn.entity.Especialidad;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EspecialidadMapper {

    public static Especialidad especialidadSaveMapper(EspecialidadDto especialidadDto){
        Especialidad especialidad = new Especialidad();
        especialidad.setDescripcion(especialidadDto.getDescripcion());
        especialidad.setNombre(especialidadDto.getNombre());
        return especialidad;
    }

    public static Set<EspecialidadFindDto> especialidadFindAllMapper(List<Especialidad> especialidades) {
        Set<Especialidad> result = new HashSet<>(especialidades);
        return result.stream().map(e -> new EspecialidadFindDto(e.getDescripcion(), e.getNombre()
        ,e.getListaProblemas(), e.getListaTecnicos())).collect(Collectors.toSet());
    }
}
