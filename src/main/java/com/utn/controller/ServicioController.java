package com.utn.controller;

import com.utn.dto.request.ServicioDto;
import com.utn.service.IServicioService;
import com.utn.service.ServicioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/servicio")
public class ServicioController {

    IServicioService service;

    public ServicioController(ServicioServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ServicioDto servicioDto){
        return new ResponseEntity<>(service.guardarServicio(servicioDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findServicioById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findServicio(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody ServicioDto servicioDto) {
        return new ResponseEntity<>(service.modificar(servicioDto), HttpStatus.OK);
    }

    @PutMapping("/asignar")
    public ResponseEntity<?> asignar(@PathVariable Long idCliente, @PathVariable Long idServicio) {
        return new ResponseEntity<>(service.asignarServicioACliente(idCliente, idServicio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
