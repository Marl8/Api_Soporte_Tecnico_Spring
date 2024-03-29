package com.utn.service.Interfaces;


import com.utn.dto.request.UserCompleteDto;
import com.utn.dto.request.UserDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseUserDto;

public interface IUserService {

    ResponseUserDto guardarUser(UserDto userDto);

    ResponseUserDto modificarUser(UserCompleteDto userDto, Long userId);

    ResponseDto deleteUser(Long id);
}
