package com.utn.utils;

import com.utn.dto.request.EspecialidadDto;
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
        especialidad.setListaProblemas(especialidadDto.getListaProblemas());
        return especialidad;
    }

    public static Set<EspecialidadDto> especialidadFindAllMapper(List<Especialidad> especialidades) {
        Set<Especialidad> result = new HashSet<>(especialidades);
        return result.stream().map(e -> new EspecialidadDto(e.getId(),
                e.getDescripcion(), e.getNombre(), e.getListaProblemas(),
                e.getListaTecnicos())).collect(Collectors.toSet());
    }
}
