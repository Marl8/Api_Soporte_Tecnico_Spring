package com.utn.controller;

import com.utn.dto.request.UserCompleteDto;
import com.utn.dto.request.UserDto;
import com.utn.service.Interfaces.IUserService;
import com.utn.service.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
@Validated
public class UserController {

    IUserService service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(service.guardarUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{userId}")
    public ResponseEntity<?> modificarUser(@Valid @RequestBody UserCompleteDto userDto, @PathVariable Long userId) {
        return new ResponseEntity<>(service.modificarUser(userDto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarUser(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.deleteUser(id), HttpStatus.OK);
    }
}
