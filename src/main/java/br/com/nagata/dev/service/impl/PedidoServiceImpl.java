package br.com.nagata.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Pedido;
import br.com.nagata.dev.repository.PedidoRepository;
import br.com.nagata.dev.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

  private PedidoRepository repository;

  @Autowired
  public PedidoServiceImpl(PedidoRepository repository) {
    this.repository = repository;
  }

  @Override
  public Pedido find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado ID: " + id + ", Tipo: " + Pedido.class.getName()));
  }
}
