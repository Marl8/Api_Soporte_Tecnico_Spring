package com.utn.controller;

import com.utn.dto.request.TipoProblemaDto;
import com.utn.service.Interfaces.IProblemaService;
import com.utn.service.ProblemaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/problema")
public class ProblemasController {

    IProblemaService service;

    public ProblemasController(ProblemaServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody TipoProblemaDto problemaDto){
        return new ResponseEntity<>(service.guardar(problemaDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTecnicoById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findProblema(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody TipoProblemaDto problema) {
        return new ResponseEntity<>(service.modificar(problema), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
