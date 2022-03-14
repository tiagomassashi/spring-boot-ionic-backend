package br.com.nagata.dev.model.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import br.com.nagata.dev.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Size(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
  private String nome;

  public CategoriaDTO(Categoria entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
  }
}
