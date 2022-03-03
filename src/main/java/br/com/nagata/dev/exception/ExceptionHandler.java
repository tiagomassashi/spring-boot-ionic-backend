package br.com.nagata.dev.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException excpetion,
      HttpServletRequest request) {
    StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), excpetion.getMessage(),
        System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException excpetion,
      HttpServletRequest request) {
    StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), excpetion.getMessage(),
        System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
