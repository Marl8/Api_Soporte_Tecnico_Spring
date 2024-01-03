package com.utn.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class EspecialidadNotFoundException extends RuntimeException{

    private HttpStatus status;

    public EspecialidadNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
