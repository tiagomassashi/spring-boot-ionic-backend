package br.com.nagata.dev.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ValidationError extends StandardError {

  private static final long serialVersionUID = 1L;

  private List<FieldMessage> errors = new ArrayList<>();

  public ValidationError(
      long timestamp, Integer status, String error, String message, String path) {
    super(timestamp, status, error, message, path);
  }

  public List<FieldMessage> getErrors() {
    return this.errors;
  }

  public void addError(String fieldName, String message) {
    this.errors.add(new FieldMessage(fieldName, message));
  }
}
