package br.com.nagata.dev.service;

import br.com.nagata.dev.model.Pedido;

public interface PedidoService {

  Pedido find(Integer id);

  Pedido insert(Pedido pedido);
}
