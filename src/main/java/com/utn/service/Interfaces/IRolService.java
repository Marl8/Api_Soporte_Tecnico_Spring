package com.utn.service.Interfaces;

import com.utn.dto.request.RolDto;
import com.utn.dto.response.ResponseRolDto;

import java.util.List;

public interface IRolService {

    ResponseRolDto save(RolDto rolDto);
    ResponseRolDto asignarRol(Long idRol, Long idUsuario);

     List<RolDto> findAll();
}
