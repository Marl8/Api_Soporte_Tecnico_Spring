package com.utn.controller;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoCompleteDto;
import com.utn.service.Interfaces.ITecnicoService;
import com.utn.service.TecnicoServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/tecnico")
@Validated
public class TecnicoController {

    ITecnicoService service;

    public TecnicoController(TecnicoServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody TecnicoDto tecnicoDto){
        return new ResponseEntity<>(service.guardar(tecnicoDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTecnicoById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findTecnico(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@Valid @RequestBody TecnicoCompleteDto tecnicoDto) {
        return new ResponseEntity<>(service.modificar(tecnicoDto), HttpStatus.OK);
    }

    @PutMapping("/asignar/{idEspecialidad}/{idTecnico}")
    public ResponseEntity<?> asignar(@PathVariable @Positive(message = "Debe ser un número positivo") Long idEspecialidad,
                                     @PathVariable @Positive(message = "Debe ser un número positivo") Long idTecnico) {
        return new ResponseEntity<>(service.asignarEspecialidadATecnico(idEspecialidad, idTecnico), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }

    @GetMapping("/incidentesResueltos/{dias}")
    public ResponseEntity<?> findTecnicoMasResueltos(@PathVariable @Positive(message = "Debe ser un número positivo") long dias) {
        return new ResponseEntity<>(service.tecnicoConMasResueltos(dias), HttpStatus.OK);
    }

    @GetMapping("/PorEspecialidadResueltos/{dias}")
    public ResponseEntity<?> findTecnicoEspecialidadMasResueltos(
            @PathVariable @Positive(message = "Debe ser un número positivo") long dias,
            @PathVariable @NotBlank(message = "Debe indicarse una especialidad") String especialidad) {
        return new ResponseEntity<>(service.tecnicoEspecialidadMasResueltos(dias, especialidad), HttpStatus.OK);
    }

    @GetMapping("/tecnicoMasRapido")
    public ResponseEntity<?> tecnicoMasRapido(){
        return  new ResponseEntity<>(service.tecnicoMasRapido(), HttpStatus.OK);
    }
}
