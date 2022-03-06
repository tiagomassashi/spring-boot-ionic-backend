package br.com.nagata.dev.model.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.service.validation.ClienteUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ClienteUpdate
public class ClienteDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
  private String nome;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Email(message = "Email obrigatório")
  private String email;

  public ClienteDTO(Cliente entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
    this.email = entity.getEmail();
  }
}
