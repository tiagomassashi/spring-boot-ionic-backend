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
            System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(),
            "Não encontrado",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(
      DataIntegrityException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            "Integridade de dados",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> argumentNotValid(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    ValidationError error =
        new ValidationError(
            System.currentTimeMillis(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Erro de validação",
            exception.getMessage(),
            request.getRequestURI());

    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(
            fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> authorization(
      AuthorizationException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            System.currentTimeMillis(),
            HttpStatus.FORBIDDEN.value(),
            "Acesso negado",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(FileException.class)
  public ResponseEntity<StandardError> file(FileException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de arquivo",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AmazonServiceException.class)
  public ResponseEntity<StandardError> amazonService(
      AmazonServiceException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            System.currentTimeMillis(),
            exception.getStatusCode(),
            "Erro Amazon Service",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode())).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AmazonClientException.class)
  public ResponseEntity<StandardError> amazonClient(
      AmazonClientException exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro Amazon Client",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AmazonS3Exception.class)
  public ResponseEntity<StandardError> amazonS3(
      AmazonS3Exception exception, HttpServletRequest request) {
    StandardError error =
        new StandardError(
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro S3",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
