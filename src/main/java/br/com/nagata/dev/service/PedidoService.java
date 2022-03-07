package br.com.nagata.dev.service;

import org.springframework.data.domain.Page;
import br.com.nagata.dev.model.Pedido;

public interface PedidoService {

  Pedido find(Integer id);

  Pedido insert(Pedido pedido);

  Page<Pedido> findPage(Integer page, Integer size, String orderBy, String direction);
}
