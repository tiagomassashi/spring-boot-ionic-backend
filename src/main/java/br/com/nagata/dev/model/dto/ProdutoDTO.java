package br.com.nagata.dev.model.dto;

import java.io.Serializable;
import br.com.nagata.dev.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nome;
  private Double preco;
  
  public ProdutoDTO(Produto entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
    this.preco = entity.getPreco();
  }
}
