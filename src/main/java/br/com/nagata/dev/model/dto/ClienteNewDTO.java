package br.com.nagata.dev.model.dto;

import java.io.Serializable;
import br.com.nagata.dev.model.enums.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteNewDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nome;
  private String email;
  private String cpgOuCnpj;
  private TipoCliente tipo;

  private String logradouro;
  private String numero;
  private String complemento;
  private String bairro;
  private String cep;

  private String telefone1;
  private String telefone2;
  private String telefone3;

  private Integer cidadeId;
}
