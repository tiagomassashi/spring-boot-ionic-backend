package br.com.nagata.dev.service.impl;

import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.model.PagamentoComBoleto;
import br.com.nagata.dev.service.BoletoService;

@Service
public class BoletoServiceImpl implements BoletoService {

  @Override
  public void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, Date instanteDoPedido) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(instanteDoPedido);
    calendar.add(Calendar.DAY_OF_MONTH, 7);
    pagamento.setDataVencimento(calendar.getTime());
  }
}
