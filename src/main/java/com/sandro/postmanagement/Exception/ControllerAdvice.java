package com.sandro.postmanagement.Exception;

import com.sandro.postmanagement.dto.ErrorDTO;
import org.apache.coyote.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> detalles = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorDTO error = new ErrorDTO("P-100", "Error de validación", detalles);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(StringContainsNumberException.class)
    public ResponseEntity<?> handlerStringContainsNumberException(StringContainsNumberException e) {
        ErrorDTO error = ErrorDTO.builder().code("P-101").debugMessage("No esta permitido ingresar numeros en el nombre").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EmptyCollectionExpcetion.class)
    public ResponseEntity<?> handlerEmptyCollectionException(EmptyCollectionExpcetion e) {
        ErrorDTO error = ErrorDTO.builder().code("P-102").debugMessage("No se ha encontrado la colección").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> handlerIdNotFoundException(IdNotFoundException e) {
        ErrorDTO error = ErrorDTO.builder().code("P-103").debugMessage("No se ha encontrado el ID ingresado").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
