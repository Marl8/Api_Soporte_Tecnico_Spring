package com.utn.service;

import com.utn.dto.request.UserCompleteDto;
import com.utn.dto.request.UserDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseUserDto;
import com.utn.entity.ERol;
import com.utn.entity.Rol;
import com.utn.entity.UserEntity;
import com.utn.exception.UserCustomException;
import com.utn.repository.RolRepository;
import com.utn.repository.UserRepository;
import com.utn.service.Interfaces.IUserService;
import com.utn.utils.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    UserRepository repository;

    RolRepository rolRepository;

    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, RolRepository rolRepository,
    PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseUserDto guardarUser(UserDto userDto) {
        UserEntity usuario = UserMapper.user(userDto);
        Optional<UserEntity> encontrado = repository.findByUsername(usuario.getUsername());

        if(encontrado.isPresent()) {
            throw new UserCustomException("El nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        // Se asigna por defecto el rol USER a un nuevo usuario
        Rol rol = rolRepository.findRolByName(ERol.USER).orElseThrow(
                () -> new RuntimeException("No existe el rol"));
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity guardado = repository.save(usuario);
        System.out.println(guardado);

        return new ResponseUserDto(guardado.getNombre(), guardado.getApellido(), "Usuario guardado con éxito");
    }

    @Override
    public ResponseUserDto modificarUser(UserCompleteDto userDto, Long userId) {
        ModelMapper mapper = new ModelMapper();
        UserEntity usuario = mapper.map(userDto, UserEntity.class);
        UserEntity encontrado = repository.findById(userId).orElseThrow(
                () -> new UserCustomException("Usuario no encontrado", HttpStatus.BAD_REQUEST)
        );
        encontrado.setNombre(usuario.getNombre());
        encontrado.setApellido(usuario.getApellido());
        encontrado.setUsername(usuario.getUsername());
        encontrado.setPassword(passwordEncoder.encode(usuario.getPassword()));
        encontrado.setRoles(usuario.getRoles());

        UserEntity modificado = repository.save(encontrado);
        return new ResponseUserDto(modificado.getNombre(), modificado.getApellido(),
                "Usuario modificado con éxito");
    }

    @Override
    public ResponseDto deleteUser(Long id) {
        UserEntity encontrado = repository.findById(id).orElseThrow(
                () -> new UserCustomException("Usuario no encontrado", HttpStatus.BAD_REQUEST)
        );
        repository.delete(encontrado);
        return new ResponseDto("Usuario eliminado con éxito");
    }


}
