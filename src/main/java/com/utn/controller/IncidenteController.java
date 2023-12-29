package com.utn.controller;

import com.utn.dto.request.IncidenteDto;
import com.utn.dto.request.IncidenteUpdateDto;
import com.utn.service.Interfaces.IIncidenteService;
import com.utn.service.IncidenteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/incidente")
public class IncidenteController {

    IIncidenteService service;

    public IncidenteController(IncidenteServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody IncidenteDto incidenteDto){
        return new ResponseEntity<>(service.guardar(incidenteDto), HttpStatus.OK);
    }

    @PutMapping("/asignarHoras/{idIncidente}/{horas}")
    public ResponseEntity<?> asignarHorasColchon(@PathVariable Long idIncidente, @PathVariable int horas){
        return new ResponseEntity<>(service.asignarHorasColchon(idIncidente, horas), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTecnicoById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findIncidente(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody IncidenteUpdateDto incidenteDto) {
        return new ResponseEntity<>(service.modificar(incidenteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
