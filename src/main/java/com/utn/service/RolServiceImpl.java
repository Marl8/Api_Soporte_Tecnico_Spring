package com.utn.service;

import com.utn.dto.request.RolDto;
import com.utn.dto.response.ResponseRolDto;
import com.utn.entity.Rol;
import com.utn.entity.UserEntity;
import com.utn.exception.RolGenericException;
import com.utn.repository.RolRepository;
import com.utn.repository.UserRepository;
import com.utn.service.Interfaces.IRolService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;


@Service
public class RolServiceImpl implements IRolService {

    RolRepository repository;

    UserRepository userRepository;

    public RolServiceImpl(RolRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseRolDto save(RolDto rolDto) {
        List<Rol> roles = repository.findAll();
        ModelMapper mapper = new ModelMapper();
        if(!roles.isEmpty()){
            for(Rol role : roles) {
                if(role.getName().name().equals(rolDto.getName().name())) {
                    throw new RolGenericException("Role " + role.getName() + " ya existe", HttpStatus.BAD_REQUEST);
                }
            }
        }
        Rol role = mapper.map(rolDto, Rol.class);
        repository.save(role);
        return new ResponseRolDto("Rol guardado con éxito");
    }

    @Override
    public ResponseRolDto asignarRol(Long idRol, Long idUsuario) {
        UserEntity usuario = userRepository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("No existen usuarios con este id"));
        Rol rol = repository.findById(idRol).orElseThrow(
                () -> new RolGenericException("No existen roles con este id", HttpStatus.BAD_REQUEST));
        Set<Rol> nuevosRoles = usuario.getRoles();
        for (Rol r : nuevosRoles) {
            if(r.getName().name().equals(rol.getName().name())) {
                throw new RolGenericException("Role " + r.getName() + " el usuario ya posee este rol",
                        HttpStatus.BAD_REQUEST);
            }
        }
        nuevosRoles.add(rol);
        usuario.setRoles(nuevosRoles);
        userRepository.save(usuario);
        return new ResponseRolDto("Rol asignado con éxito");
    }

    @Override
    public List<RolDto> findAll() {
        List<Rol> lista = repository.findAll();
        if(lista.isEmpty()) {
            throw new RolGenericException("No se han encontrado roles", HttpStatus.BAD_REQUEST);
        }
        ModelMapper mapper = new ModelMapper();
        return lista.stream()
                .map(r -> mapper.map(r, RolDto.class))
                .toList();
    }
}
