package br.com.nagata.dev.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(
      ObjectNotFoundException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(
      DataIntegrityException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> argumentNotValid(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    ValidationError error =
        new ValidationError(
            HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());

    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(
            fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> authorization(
      AuthorizationException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            HttpStatus.FORBIDDEN.value(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(FileException.class)
  public ResponseEntity<StandardError> file(FileException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AmazonServiceException.class)
  public ResponseEntity<StandardError> amazonService(
      AmazonServiceException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            exception.getStatusCode(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode())).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AmazonClientException.class)
  public ResponseEntity<StandardError> amazonClient(
      AmazonClientException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AmazonS3Exception.class)
  public ResponseEntity<StandardError> amazonS3(
      AmazonS3Exception exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
