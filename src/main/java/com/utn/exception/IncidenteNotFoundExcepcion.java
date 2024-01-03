package com.utn.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class IncidenteNotFoundExcepcion extends RuntimeException{

    private HttpStatus status;

    public IncidenteNotFoundExcepcion(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
