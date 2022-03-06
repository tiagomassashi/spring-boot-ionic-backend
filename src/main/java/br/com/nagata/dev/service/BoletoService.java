package br.com.nagata.dev.service;

import java.util.Date;
import br.com.nagata.dev.model.PagamentoComBoleto;

public interface BoletoService {

  void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, Date instanteDoPedido);
}
