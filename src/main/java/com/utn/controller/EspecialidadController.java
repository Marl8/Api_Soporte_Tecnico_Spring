package com.utn.controller;

import com.utn.dto.request.EspecialidadDto;
import com.utn.service.Interfaces.IEspecialidadService;
import com.utn.service.EspecialidadServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/especialidad")
public class EspecialidadController {

    IEspecialidadService service;

    public EspecialidadController(EspecialidadServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody EspecialidadDto especialidadDto){
        return new ResponseEntity<>(service.guardar(especialidadDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEspecialidadById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findEspecialidad(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody EspecialidadDto especialidadDto) {
        return new ResponseEntity<>(service.modificar(especialidadDto), HttpStatus.OK);
    }

    @PutMapping("/asignar/{idEspecialidad}/{idProblema}")
    public ResponseEntity<?> asignar(@PathVariable Long idEspecialidad, @PathVariable Long idTecnico) {
        return new ResponseEntity<>(service.asignarProblema(idEspecialidad, idTecnico), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
