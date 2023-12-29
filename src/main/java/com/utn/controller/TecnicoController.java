package com.utn.controller;

import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoUpdateDto;
import com.utn.service.Interfaces.ITecnicoService;
import com.utn.service.TecnicoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/tecnico")
public class TecnicoController {

    ITecnicoService service;

    public TecnicoController(TecnicoServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody TecnicoDto tecnicoDto){
        return new ResponseEntity<>(service.guardar(tecnicoDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTecnicoById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findTecnico(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody TecnicoUpdateDto tecnicoDto) {
        return new ResponseEntity<>(service.modificar(tecnicoDto), HttpStatus.OK);
    }

    @PutMapping("/asignar/{idEspecialidad}/{idTecnico}")
    public ResponseEntity<?> asignar(@PathVariable Long idEspecialidad, @PathVariable Long idTecnico) {
        return new ResponseEntity<>(service.asignarEspecialidadATecnico(idEspecialidad, idTecnico), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
