package br.com.nagata.dev.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import br.com.nagata.dev.exception.FieldMessage;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.dto.ClienteDTO;
import br.com.nagata.dev.repository.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

  private final HttpServletRequest request;
  private final ClienteRepository repository;

  @Autowired
  public ClienteUpdateValidator(HttpServletRequest request, ClienteRepository repository) {
    this.request = request;
    this.repository = repository;
  }

  @Override
  public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    Map<String, String> map =
        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

    Integer uriId = Integer.parseInt(map.get("id"));

    Optional<Cliente> cliente = repository.findByEmail(value.getEmail());

    if (cliente.isPresent() && !cliente.get().getId().equals(uriId)) {
      list.add(new FieldMessage("email", "Email já existente"));
    }

    list.forEach(
        fieldMessage -> {
          context.disableDefaultConstraintViolation();
          context
              .buildConstraintViolationWithTemplate(fieldMessage.getMessage())
              .addPropertyNode(fieldMessage.getFieldName())
              .addConstraintViolation();
        });

    return list.isEmpty();
  }
}
