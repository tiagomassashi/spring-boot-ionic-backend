package br.com.nagata.dev.converter;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import br.com.nagata.dev.model.enums.TipoCliente;

@Converter(autoApply = true)
public class TipoClienteConverter implements AttributeConverter<TipoCliente, Integer> {

  @Override
  public Integer convertToDatabaseColumn(TipoCliente tipoCliente) {
    if (tipoCliente == null) {
      return null;
    }
    return tipoCliente.getCodigo();
  }

  @Override
  public TipoCliente convertToEntityAttribute(Integer codigo) {
    if (codigo == null) {
      return null;
    }
    return Stream.of(TipoCliente.values())
        .filter(c -> c.getCodigo().equals(codigo))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
