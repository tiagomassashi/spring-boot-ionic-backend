package br.com.nagata.dev.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ValidationError extends StandardError {

  private static final long serialVersionUID = 1L;

  private List<FieldMessage> errors = new ArrayList<>();

  public ValidationError(int value, String message, long currentTimeMillis) {
    super(value, message, currentTimeMillis);
  }

  public List<FieldMessage> getErrors() {
    return this.errors;
  }

  public void addError(String fieldName, String message) {
    this.errors.add(new FieldMessage(fieldName, message));
  }
}
