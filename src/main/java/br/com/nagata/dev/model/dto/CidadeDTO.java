package br.com.nagata.dev.model.dto;

import br.com.nagata.dev.model.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private int id;
  private String nome;

  public CidadeDTO(Cidade entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
  }
}
