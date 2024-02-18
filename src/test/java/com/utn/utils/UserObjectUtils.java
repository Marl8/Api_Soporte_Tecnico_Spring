package com.utn.utils;

import com.utn.dto.request.UserCompleteDto;
import com.utn.dto.request.UserDto;
import com.utn.entity.UserEntity;

public class UserObjectUtils {

    public static UserEntity user1(){
        UserEntity user = new UserEntity();
        user.setNombre("Roberto");
        user.setApellido("Gonzalez");
        user.setUsername("rober");
        user.setPassword("123");
        return user;
    }

    public static UserEntity user2(){
        UserEntity user = new UserEntity();
        user.setNombre("Anibal");
        user.setApellido("Flores");
        user.setUsername("A.flores");
        user.setPassword("123");
        return user;
    }

    public static UserEntity user3(){
        UserEntity user = new UserEntity();
        user.setNombre("Anibal");
        user.setApellido("Flores");
        user.setUsername("A.flores");
        user.setPassword("123");
        user.setRoles(RolObjectUtils.setRoles());
        return user;
    }

    public static UserDto userDto1(){
        UserDto user = new UserDto();
        user.setNombre("Roberto");
        user.setApellido("Gonzalez");
        user.setUsername("rober");
        user.setPassword("123");
        return user;
    }

    public static UserDto userDto2(){
        UserDto user = new UserDto();
        user.setNombre("Anibal");
        user.setApellido("Flores");
        user.setUsername("A.flores");
        user.setPassword("123");
        return user;
    }

    public static UserCompleteDto userCompleteDto(){
        UserCompleteDto user = new UserCompleteDto();
        user.setNombre("Anibal");
        user.setApellido("Flores");
        user.setUsername("A.flores");
        user.setPassword("123");
        user.setRoles(RolObjectUtils.setRoles());
        return user;
    }
}
