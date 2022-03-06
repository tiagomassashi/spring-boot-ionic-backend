package br.com.nagata.dev.converter;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import br.com.nagata.dev.model.enums.Perfil;

@Converter(autoApply = true)
public class PerfilConverter implements AttributeConverter<Perfil, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Perfil perfil) {
    if (perfil == null) {
      return null;
    }
    return perfil.getCodigo();
  }

  @Override
  public Perfil convertToEntityAttribute(Integer codigo) {
    if (codigo == null) {
      return null;
    }
    return Stream.of(Perfil.values()).filter(c -> c.getCodigo().equals(codigo)).findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
