package com.utn.controller;

import com.utn.dto.request.OperadorDto;
import com.utn.service.IOperadorService;
import com.utn.service.OperadorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/operador")
public class OperadorController {

    IOperadorService service;

    public OperadorController(OperadorServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody OperadorDto operadorDto){
        return new ResponseEntity<>(service.guardar(operadorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTecnicoById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findOperador(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody OperadorDto operadorDto) {
        return new ResponseEntity<>(service.modificar(operadorDto), HttpStatus.OK);
    }

    @PutMapping("listaTecnicos/{idOperador}")
    public ResponseEntity<?> asignarListaTecnicos(@PathVariable Long idOperador) {
        return new ResponseEntity<>(service.asignarListaTecnico(idOperador), HttpStatus.OK);
    }

    @PutMapping("/asignarTecnico/{idIncidente}")
    public ResponseEntity<?> asignarTecnicoAIncidente(@PathVariable Long idIncidente) {
        return new ResponseEntity<>(service.asignarTecnico(idIncidente), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
