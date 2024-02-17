package com.utn.exception;

import com.utn.dto.response.ErrorValidDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

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

    @ExceptionHandler(IncidenteNotFoundException.class)
    public ResponseEntity<?> clienteNotFoundException(IncidenteNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(OperadorNotFoundException.class)
    public ResponseEntity<?> operadorNotFoundException(OperadorNotFoundException ex){
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

    @ExceptionHandler(UserCustomException.class)
    public ResponseEntity<?> userCustomException(UserCustomException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(RolGenericException.class)
    public ResponseEntity<?> userCustomException(RolGenericException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> fallaValidacion(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> fallanVariasValidaciones(MethodArgumentNotValidException ex){

        HashMap<String, String> errores = new HashMap<>();
        ex.getFieldErrors()
                .forEach(field -> errores.put(field.getField(), field.getDefaultMessage()));

        return new ResponseEntity<>(new ErrorValidDto(400, errores), HttpStatus.BAD_REQUEST);
    }

}
