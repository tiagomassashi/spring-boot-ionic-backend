package br.com.nagata.dev.model;

import java.util.Date;
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
public class PagamentoComBoleto extends Pagamento {

  private static final long serialVersionUID = 1L;

  private Date dataVencimento;
  private Date dataPagamento;
}
