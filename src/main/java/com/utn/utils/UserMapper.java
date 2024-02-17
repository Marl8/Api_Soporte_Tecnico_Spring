package com.utn.utils;

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
}
