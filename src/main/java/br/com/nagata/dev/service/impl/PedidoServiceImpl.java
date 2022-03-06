package br.com.nagata.dev.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.PagamentoComBoleto;
import br.com.nagata.dev.model.Pedido;
import br.com.nagata.dev.model.enums.EstadoPagamento;
import br.com.nagata.dev.repository.ItemPedidoRepository;
import br.com.nagata.dev.repository.PagamentoRepository;
import br.com.nagata.dev.repository.PedidoRepository;
import br.com.nagata.dev.service.BoletoService;
import br.com.nagata.dev.service.PedidoService;
import br.com.nagata.dev.service.ProdutoService;

@Service
public class PedidoServiceImpl implements PedidoService {

  private PedidoRepository repository;
  private BoletoService boletoService;
  private PagamentoRepository pagamentoRepository;
  private ProdutoService produtoService;
  private ItemPedidoRepository itemPedidoRepository;

  @Autowired
  public PedidoServiceImpl(PedidoRepository repository, BoletoService boletoService,
      PagamentoRepository pagamentoRepository, ProdutoService produtoService,
      ItemPedidoRepository itemPedidoRepository) {
    this.repository = repository;
    this.boletoService = boletoService;
    this.pagamentoRepository = pagamentoRepository;
    this.produtoService = produtoService;
    this.itemPedidoRepository = itemPedidoRepository;
  }

  @Override
  public Pedido find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado ID: " + id + ", Tipo: " + Pedido.class.getName()));
  }

  @Override
  public Pedido insert(Pedido pedido) {
    pedido.setId(null);
    pedido.setInstante(new Date());
    pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
    pedido.getPagamento().setPedido(pedido);

    if (pedido.getPagamento() instanceof PagamentoComBoleto) {
      PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
      boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
    }

    final Pedido newPedido = repository.save(pedido);
    pagamentoRepository.save(newPedido.getPagamento());

    newPedido.getItens().stream().forEach(item -> {
      item.setDesconto(0.0);
      item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
      item.setPedido(newPedido);
    });

    itemPedidoRepository.saveAll(newPedido.getItens());

    return newPedido;
  }
}
