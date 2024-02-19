package com.utn.controller;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.request.ClienteUpdateDto;
import com.utn.service.ClienteServiceImpl;
import com.utn.service.Interfaces.IClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/cliente")
@Validated
public class ClienteController {

    IClienteService service;

    public ClienteController(ClienteServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@Valid @RequestBody ClienteDto clienteDto){
        return new ResponseEntity<>(service.guardarCliente(clienteDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClienteById(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.findCliente(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificar(@Valid @RequestBody ClienteUpdateDto clienteDto,
                                       @Positive(message = "Debe ser un número positivo") @PathVariable Long id) {
        return new ResponseEntity<>(service.modificar(clienteDto, id), HttpStatus.OK);
    }

    @PutMapping("/asignar/{idCliente}/{idServicio}")
    public ResponseEntity<?> asignarServicio(@PathVariable @Positive(message = "Debe ser un número positivo") Long idCliente,
                                             @Positive(message = "Debe ser un número positivo") @PathVariable Long idServicio) {
        return new ResponseEntity<>(service.asignarServicioACliente(idCliente, idServicio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Debe ser un número positivo") Long id) {
        return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
    }
}
