package com.utn.service;

import com.utn.dto.request.LoginDto;
import com.utn.dto.response.JwtDto;
import com.utn.entity.UserEntity;
import com.utn.repository.UserRepository;
import com.utn.service.Interfaces.ILoginService;
import com.utn.service.securityServices.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl  implements ILoginService{

    AuthenticationManager authenticationManager;

    UserRepository repository;

    JwtService jwtService;

    public LoginServiceImpl(AuthenticationManager authenticationManager,
                            UserRepository repository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @Override
    public JwtDto login(LoginDto loginDto) {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());
        authenticationManager.authenticate(auth);

        UserEntity user = repository.findByUsername(loginDto.getUsername()).get();
        String jwt = jwtService.generate(user, generateExtraClaims(user));
        return new JwtDto(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();

        // Obtengo el nombre real de Usuario
        extraClaims.put("name", user.getNombre() + " " + user.getApellido());
        // Obtengo la lista de roles asignados al usuario
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        return extraClaims;
    }
}
