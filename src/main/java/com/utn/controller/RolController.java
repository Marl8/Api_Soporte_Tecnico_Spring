package com.utn.controller;

import com.utn.dto.request.RolDto;
import com.utn.service.Interfaces.IRolService;
import com.utn.service.RolServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/rol")
public class RolController {

    IRolService service;

    public RolController(RolServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody RolDto rolDto){
        return new ResponseEntity<>(service.save(rolDto), HttpStatus.CREATED);
    }

    @PutMapping("/{idRol}/{idUsuario}")
    public ResponseEntity<?> asignarRol(@PathVariable @Positive Long idRol,
                                        @PathVariable @Positive Long idUsuario){
        return new ResponseEntity<>(service.asignarRol(idRol, idUsuario), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
