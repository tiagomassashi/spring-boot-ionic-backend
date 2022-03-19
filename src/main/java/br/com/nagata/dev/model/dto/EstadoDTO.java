package br.com.nagata.dev.model.dto;

import br.com.nagata.dev.model.Estado;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EstadoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nome;

  public EstadoDTO(Estado entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
  }
}
