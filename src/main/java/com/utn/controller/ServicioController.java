package com.utn.controller;

import com.utn.dto.request.ServicioDto;
import com.utn.service.Interfaces.IServicioService;
import com.utn.service.ServicioServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/servicio")
@Validated
public class ServicioController {

    IServicioService service;

    public ServicioController(ServicioServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ServicioDto servicioDto){
        return new ResponseEntity<>(service.guardarServicio(servicioDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findServicioById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findServicio(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@Valid @RequestBody ServicioDto servicioDto) {
        return new ResponseEntity<>(service.modificar(servicioDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
