package com.utn.controller;

import com.utn.dto.request.LoginDto;
import com.utn.service.Interfaces.ILoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/login")
public class LoginController {

    ILoginService service;

    public LoginController(ILoginService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> login (@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(service.login(loginDto), HttpStatus.OK);
    }
}
