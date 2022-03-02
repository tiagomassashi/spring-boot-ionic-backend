package br.com.nagata.dev.converter;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import br.com.nagata.dev.model.enums.EstadoPagamento;

@Converter(autoApply = true)
public class EstadoPagamentoConverter implements AttributeConverter<EstadoPagamento, Integer> {

  @Override
  public Integer convertToDatabaseColumn(EstadoPagamento estadoPagamento) {
    if (estadoPagamento == null) {
      return null;
    }
    return estadoPagamento.getCodigo();
  }

  @Override
  public EstadoPagamento convertToEntityAttribute(Integer codigo) {
    if (codigo == null) {
      return null;
    }
    return Stream.of(EstadoPagamento.values()).filter(c -> c.getCodigo().equals(codigo)).findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
