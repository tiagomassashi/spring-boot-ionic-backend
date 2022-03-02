package br.com.nagata.dev.model;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PagamentoComCartao extends Pagamento {

  private static final long serialVersionUID = 1L;

  private Integer numeroDeParcelas;
}
