package com.utn.controller;

import com.utn.dto.request.TipoProblemaCompleteDto;
import com.utn.dto.request.TipoProblemaDto;
import com.utn.service.Interfaces.IProblemaService;
import com.utn.service.ProblemaServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/problema")
@Validated
public class ProblemasController {

    IProblemaService service;

    public ProblemasController(ProblemaServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody TipoProblemaDto problemaDto){
        return new ResponseEntity<>(service.guardar(problemaDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProblemaById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findProblema(id), HttpStatus.OK);
    }

    @PutMapping("modificar/{idProblema}")
    public ResponseEntity<?> modificar(@Valid @RequestBody TipoProblemaCompleteDto problema, @Valid
                                        @PathVariable Long idProblema) {
        return new ResponseEntity<>(service.modificar(problema, idProblema), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
