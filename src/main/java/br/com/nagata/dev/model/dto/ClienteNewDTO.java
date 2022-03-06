package br.com.nagata.dev.model.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import br.com.nagata.dev.model.enums.TipoCliente;
import br.com.nagata.dev.service.validation.ClienteInsert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ClienteInsert
public class ClienteNewDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
  private String nome;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Email(message = "Email obrigatório")
  private String email;

  @NotEmpty(message = "Preenchimento obrigatório")
  private String cpfOuCnpj;

  private TipoCliente tipo;
  
  @NotEmpty(message = "Preenchimento obrigatório")
  private String senha;

  @NotEmpty(message = "Preenchimento obrigatório")
  private String logradouro;

  @NotEmpty(message = "Preenchimento obrigatório")
  private String numero;

  private String complemento;

  private String bairro;

  @NotEmpty(message = "Preenchimento obrigatório")
  private String cep;

  @NotEmpty(message = "Preenchimento obrigatório")
  private String telefone1;

  private String telefone2;

  private String telefone3;

  private Integer cidadeId;
}
