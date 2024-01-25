package com.utn.controller;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.request.EspecialidadUpdateDto;
import com.utn.service.Interfaces.IEspecialidadService;
import com.utn.service.EspecialidadServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/especialidad")
@Validated
public class EspecialidadController {

    IEspecialidadService service;

    public EspecialidadController(EspecialidadServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody EspecialidadDto especialidadDto){
        return new ResponseEntity<>(service.guardar(especialidadDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEspecialidadById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findEspecialidad(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@Valid @RequestBody EspecialidadUpdateDto especialidadDto) {
        return new ResponseEntity<>(service.modificar(especialidadDto), HttpStatus.OK);
    }

    @PutMapping("/asignar/{idEspecialidad}/{idProblema}")
    public ResponseEntity<?> asignar(@PathVariable @Positive(message = "Debe ser un número positivo") Long idEspecialidad,
                                     @Positive(message = "Debe ser un número positivo") @PathVariable Long idProblema) {
        return new ResponseEntity<>(service.asignarProblema(idEspecialidad, idProblema), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
