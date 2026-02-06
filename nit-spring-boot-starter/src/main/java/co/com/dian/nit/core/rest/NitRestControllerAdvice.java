package co.com.dian.nit.core.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.com.dian.nit.core.error.NitValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Advice para manejo de excepciones en controladores REST.
 * Implementa RFC 9457 (Problem Details for HTTP APIs).
 */
@RestControllerAdvice
public class NitRestControllerAdvice {

    /**
     * Maneja errores de validación de Bean Validation (@Valid).
     * 
     * @param ex excepción
     * @return detalle del problema
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación en los campos");
        problem.setTitle("Validación Fallida");
        problem.setType(URI.create("https://dian.gov.co/errors/validation-failed"));
        problem.setProperty("timestamp", Instant.now());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        problem.setProperty("errors", errors);

        return problem;
    }
    
    /**
     * Maneja excepciones de validación de NIT.
     * 
     * @param ex excepción
     * @return detalle del problema
     */
    @ExceptionHandler(NitValidationException.class)
    public ProblemDetail handleNitValidationException(NitValidationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Error de Validación de NIT");
        problem.setType(URI.create("https://dian.gov.co/errors/invalid-nit"));
        problem.setProperty("timestamp", Instant.now());
        
        if (ex.getResult() != null) {
            problem.setProperty("base", ex.getResult().getBaseNumber());
            problem.setProperty("expected", ex.getResult().getExpectedDigit());
            problem.setProperty("provided", ex.getResult().getProvidedDigit());
            problem.setProperty("nitType", ex.getResult().getType());
        }
        
        if (ex.getErrorCode() != null) {
            problem.setProperty("errorCode", ex.getErrorCode().getCode());
        }
        
        return problem;
    }

    /**
     * Maneja excepciones de argumento inválido.
     * 
     * @param ex excepción
     * @return detalle del problema
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Argumento Inválido");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    /**
     * Maneja excepciones genéricas.
     * 
     * @param ex excepción
     * @return detalle del problema
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ex.printStackTrace(); // Imprimir error real en consola
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado: " + ex.getMessage());
        problem.setTitle("Error Interno del Servidor");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
