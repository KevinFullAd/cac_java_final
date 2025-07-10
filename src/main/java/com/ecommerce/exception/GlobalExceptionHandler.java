package com.ecommerce.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String campo = error.getPropertyPath().toString();
            String mensaje = error.getMessage();
            errores.put(campo, mensaje);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("errors", errores);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> body = new HashMap<>();
        body.put("errors", errores);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEnumParseError(HttpMessageNotReadableException ex) {
        String msg = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        Map<String, String> body = new HashMap<>();

        if (msg != null && msg.toLowerCase().contains("categoria")) {
            body.put("error", "Categoría inválida. Por favor seleccioná una categoría válida.");
        } else {
            body.put("error", "Error en el formato del JSON.");
        }

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(PedidoInvalidoException.class)
    public ResponseEntity<Map<String, String>> pedidoInvalido(PedidoInvalidoException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<?> handleUnrecognizedField(UnrecognizedPropertyException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Campo no reconocido: '" + ex.getPropertyName() + "'"
        ));
    }


    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Map<String, String>> stockError(StockInsuficienteException e) {
        Map<String, String> body = new HashMap<>();
        body.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> recursoError(RecursoNoEncontradoException e) {
        Map<String, String> body = new HashMap<>();
        body.put("error", e.getMessage());
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> errorGenerico(Exception e) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Error interno del servidor.");
        return ResponseEntity.status(500).body(body);
    }
}
