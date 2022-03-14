package br.com.nagata.dev.service.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.nagata.dev.exception.FieldMessage;
import br.com.nagata.dev.model.dto.ClienteNewDTO;
import br.com.nagata.dev.model.enums.TipoCliente;
import br.com.nagata.dev.repository.ClienteRepository;
import br.com.nagata.dev.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

  private final ClienteRepository repository;

  @Autowired
  public ClienteInsertValidator(ClienteRepository repository) {
    this.repository = repository;
  }

  @Override
  public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    if (TipoCliente.PESSOAFISICA == value.getTipo() && !BR.isValidCPF(value.getCpfOuCnpj())) {
      list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
    }

    if (TipoCliente.PESSOAJURIDICA == value.getTipo() && !BR.isValidCPF(value.getCpfOuCnpj())) {
      list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
    }

    if (repository.findByEmail(value.getEmail()).isPresent()) {
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
