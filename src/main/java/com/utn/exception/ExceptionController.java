package com.utn.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<?> clienteNotFoundException(ClienteNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(EspecialidadNotFoundException.class)
    public ResponseEntity<?> especialidadNotFoundException(EspecialidadNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(IncidenteNotFoundExcepcion.class)
    public ResponseEntity<?> clienteNotFoundException(IncidenteNotFoundExcepcion ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(OperadorNotFoundEception.class)
    public ResponseEntity<?> operadorNotFoundException(OperadorNotFoundEception ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(ServicioNotFoundException.class)
    public ResponseEntity<?> servicioNotFoundException(ServicioNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(TecnicoNotFoundException.class)
    public ResponseEntity<?>  tecnicoNotFoundException(TecnicoNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(ProblemaNotFoundException.class)
    public ResponseEntity<?> problemaNotFoundException(ProblemaNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
