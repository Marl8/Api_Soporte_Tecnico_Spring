package com.utn.utils;

import com.utn.dto.request.UserCompleteDto;
import com.utn.dto.request.UserDto;
import com.utn.entity.UserEntity;

public class UserMapper {

    public static UserEntity user(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserEntity userUpdate(UserCompleteDto dto) {
        UserEntity user = new UserEntity();
        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());
        return user;
    }
}
