package br.com.nagata.dev.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception,
      HttpServletRequest request) {
    StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), exception.getMessage(),
        System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException exception,
      HttpServletRequest request) {
    StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
        System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> argumentNotValid(MethodArgumentNotValidException exception,
      HttpServletRequest request) {
    ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
        System.currentTimeMillis());

    exception.getBindingResult().getFieldErrors().stream().forEach(
        fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
