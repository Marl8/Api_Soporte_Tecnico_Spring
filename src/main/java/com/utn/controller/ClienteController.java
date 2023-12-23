package com.utn.controller;

import com.utn.dto.request.ClienteDto;
import com.utn.service.ClienteServiceImpl;
import com.utn.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/cliente")
public class ClienteController {

    IClienteService service;

    public ClienteController(ClienteServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ClienteDto clienteDto){
        return new ResponseEntity<>(service.guardarCliente(clienteDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClienteById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findCliente(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody ClienteDto clienteDto) {
        return new ResponseEntity<>(service.modificar(clienteDto), HttpStatus.OK);
    }

    @PutMapping("/asignar/{idCliente}/{idServicio}")
    public ResponseEntity<?> asignarServicio(@PathVariable Long idCliente, @PathVariable Long idServicio) {
        return new ResponseEntity<>(service.asignarServicioACliente(idCliente, idServicio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
