package com.utn.utils;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.request.EspecialidadFindDto;
import com.utn.dto.request.EspecialidadUpdateDto;
import com.utn.entity.Especialidad;
import com.utn.entity.Tecnico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EspecialidadObjectUtils {

    public static Especialidad especialidad(){
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre("Soporte Tango");
        especialidad.setDescripcion("Soporte técnico del software Tango");
        especialidad.setListaTecnicos(new HashSet<>());
        especialidad.setListaProblemas(new HashSet<>());
        return especialidad;
    }

    public static Especialidad especialidad2(){
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre("Soporte Lex Doctor");
        especialidad.setDescripcion("Soporte técnico del software Lex Doctor");
        return especialidad;
    }

    public static EspecialidadDto especialidadDto () {
        EspecialidadDto especialidad = new EspecialidadDto();
        especialidad.setNombre("Soporte Tango");
        especialidad.setDescripcion("Soporte técnico del software Tango");
        Long id = 1L;
        Set<Long> idEspecialidades = new HashSet<>();
        idEspecialidades.add(id);
        especialidad.setListaProblemasId(idEspecialidades);
        return especialidad;
    }

    public static EspecialidadUpdateDto especialidadUpdateDto () {
        EspecialidadUpdateDto especialidad = new EspecialidadUpdateDto();
        especialidad.setNombre("Soporte Windows");
        especialidad.setDescripcion("Soporte técnico del sistema operativo Windows");
        return especialidad;
    }

    public static EspecialidadFindDto especialidadFindDto () {
        EspecialidadFindDto especialidad = new EspecialidadFindDto();
        especialidad.setNombre("Soporte Tango");
        especialidad.setDescripcion("Soporte técnico del software Tango");
        especialidad.setListaTecnicos(new HashSet<>());
        especialidad.setListaProblemas(new HashSet<>());
        return especialidad;
    }

    public static List<Especialidad> listaEspecialidades(){
        return List.of(especialidad2());
    }

    public static Set<Especialidad> listaSetEspecialidades(){
        return Set.of(especialidad());
    }
}
