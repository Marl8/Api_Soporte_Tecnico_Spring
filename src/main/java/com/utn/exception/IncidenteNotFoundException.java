package com.utn.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class IncidenteNotFoundException extends RuntimeException{

    private HttpStatus status;

    public IncidenteNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
