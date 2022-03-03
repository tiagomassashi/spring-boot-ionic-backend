package br.com.nagata.dev.model.dto;

import java.io.Serializable;
import br.com.nagata.dev.model.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nome;

  public CategoriaDTO(Categoria entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
  }
}
