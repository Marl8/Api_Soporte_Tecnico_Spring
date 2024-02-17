package com.utn.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Objects;

@Getter
@Setter
public class RolGenericException extends RuntimeException{

    private HttpStatus status;

    public RolGenericException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolGenericException that = (RolGenericException) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
