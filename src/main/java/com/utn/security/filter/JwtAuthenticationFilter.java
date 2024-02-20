package com.utn.security.filter;

import com.utn.entity.UserEntity;
import com.utn.exception.UserCustomException;
import com.utn.repository.UserRepository;
import com.utn.service.securityServices.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService jwtService;

    UserRepository repository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Capturo el header del Jwt
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        // 2. Extraer el token del header
        String token = authHeader.replace("Bearer ", "");

        // 3. Extraer el subject(username) del token
        String username = jwtService.extractUsername(token);

        // 4. Setear un objeto Authentication dentro del Security Context
        UserEntity user = repository.findByUsername(username).get();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
