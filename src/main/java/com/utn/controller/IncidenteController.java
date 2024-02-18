package com.utn.controller;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.service.Interfaces.IIncidenteService;
import com.utn.service.IncidenteServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/incidente")
@Validated
public class IncidenteController {

    IIncidenteService service;

    public IncidenteController(IncidenteServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@Valid @RequestBody IncidenteDto incidenteDto){
        return new ResponseEntity<>(service.guardar(incidenteDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findIncidenteById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findIncidente(id), HttpStatus.OK);
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<?> modificar(@Valid @RequestBody IncidenteUpdateDto incidenteDto, @PathVariable
                                       @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.modificar(incidenteDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
