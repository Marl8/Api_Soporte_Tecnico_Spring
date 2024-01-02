package com.utn.controller;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoUpdateDto;
import com.utn.service.Interfaces.ITecnicoService;
import com.utn.service.TecnicoServiceImpl;
import jakarta.validation.Valid;
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
        return new ResponseEntity<>(service.guardar(tecnicoDto), HttpStatus.OK);
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
    public ResponseEntity<?> modificar(@Valid @RequestBody TecnicoUpdateDto tecnicoDto) {
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
}
