package br.com.nagata.dev.service.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.com.nagata.dev.exception.FieldMessage;
import br.com.nagata.dev.model.dto.ClienteNewDTO;
import br.com.nagata.dev.model.enums.TipoCliente;
import br.com.nagata.dev.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

  @Override
  public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    if (TipoCliente.PESSOAFISICA == value.getTipo() && !BR.isValidCPF(value.getCpfOuCnpj())) {
      list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
    }

    if (TipoCliente.PESSOAJURIDICA == value.getTipo() && !BR.isValidCPF(value.getCpfOuCnpj())) {
      list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
    }

    list.stream().forEach(fieldMessage -> {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
          .addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
    });

    return list.isEmpty();
  }

}
