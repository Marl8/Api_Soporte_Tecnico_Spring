package com.utn.service.Interfaces;

import com.utn.dto.request.LoginDto;
import com.utn.dto.response.JwtDto;

public interface ILoginService {

    JwtDto login(LoginDto loginDto);
}
