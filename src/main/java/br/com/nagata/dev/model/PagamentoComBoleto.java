package br.com.nagata.dev.model;

import java.util.Date;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataVencimento;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPagamento;
}
