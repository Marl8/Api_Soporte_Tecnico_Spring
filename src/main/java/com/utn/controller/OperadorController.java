package com.utn.controller;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.request.OperadorUpdateDto;
import com.utn.service.Interfaces.IOperadorService;
import com.utn.service.OperadorServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/operador")
@Validated
public class OperadorController {

    IOperadorService service;

    public OperadorController(OperadorServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody OperadorDto operadorDto){
        return new ResponseEntity<>(service.guardar(operadorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTecnicoById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findOperador(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@Valid @RequestBody OperadorUpdateDto operadorDto) {
        return new ResponseEntity<>(service.modificar(operadorDto), HttpStatus.OK);
    }

    @PutMapping("listaTecnicos/{idOperador}")
    public ResponseEntity<?> asignarListaTecnicos(@PathVariable @Positive(message = "Debe ser un número positivo") Long idOperador) {
        return new ResponseEntity<>(service.asignarListaTecnico(idOperador), HttpStatus.OK);
    }

    @PutMapping("/asignarHoras/{idIncidente}/{horas}")
    public ResponseEntity<?> asignarHorasColchon(
            @PathVariable @Positive(message = "Debe ser un número positivo") Long idIncidente,
            @Positive(message = "Debe ser un número positivo") @PathVariable int horas){
        return new ResponseEntity<>(service.asignarHorasColchon(idIncidente, horas), HttpStatus.OK);
    }


    @PutMapping("/asignarTecnico/{idIncidente}")
    public ResponseEntity<?> asignarTecnicoAIncidente(@PathVariable @Positive(message = "Debe ser un número positivo") Long idIncidente) {
        return new ResponseEntity<>(service.asignarTecnico(idIncidente), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
