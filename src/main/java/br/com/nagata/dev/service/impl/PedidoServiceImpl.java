package br.com.nagata.dev.service.impl;

import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.AuthorizationException;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.PagamentoComBoleto;
import br.com.nagata.dev.model.Pedido;
import br.com.nagata.dev.model.enums.EstadoPagamento;
import br.com.nagata.dev.repository.ItemPedidoRepository;
import br.com.nagata.dev.repository.PagamentoRepository;
import br.com.nagata.dev.repository.PedidoRepository;
import br.com.nagata.dev.security.UserSS;
import br.com.nagata.dev.service.BoletoService;
import br.com.nagata.dev.service.ClienteService;
import br.com.nagata.dev.service.EmailService;
import br.com.nagata.dev.service.PedidoService;
import br.com.nagata.dev.service.ProdutoService;

@Service
public class PedidoServiceImpl implements PedidoService {

  private final PedidoRepository repository;
  private final BoletoService boletoService;
  private final PagamentoRepository pagamentoRepository;
  private final ProdutoService produtoService;
  private final ItemPedidoRepository itemPedidoRepository;
  private final ClienteService clienteService;
  private final EmailService emailService;

  @Autowired
  public PedidoServiceImpl(
      PedidoRepository repository,
      BoletoService boletoService,
      PagamentoRepository pagamentoRepository,
      ProdutoService produtoService,
      ItemPedidoRepository itemPedidoRepository,
      ClienteService clienteService,
      EmailService emailService) {
    this.repository = repository;
    this.boletoService = boletoService;
    this.pagamentoRepository = pagamentoRepository;
    this.produtoService = produtoService;
    this.itemPedidoRepository = itemPedidoRepository;
    this.clienteService = clienteService;
    this.emailService = emailService;
  }

  @Override
  public Pedido find(Integer id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ObjectNotFoundException(
                    "Objeto n??o encontrado ID: " + id + ", Tipo: " + Pedido.class.getName()));
  }

  @Transactional
  @Override
  public Pedido insert(Pedido pedido) {
    pedido.setId(null);
    pedido.setInstante(new Date());
    pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
    pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
    pedido.getPagamento().setPedido(pedido);

    if (pedido.getPagamento() instanceof PagamentoComBoleto) {
      PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
      boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
    }

    final Pedido newPedido = repository.save(pedido);
    pagamentoRepository.save(newPedido.getPagamento());

    newPedido
        .getItens()
        .forEach(
            item -> {
              item.setDesconto(0.0);
              item.setProduto(produtoService.find(item.getProduto().getId()));
              item.setPreco(item.getProduto().getPreco());
              item.setPedido(newPedido);
            });

    itemPedidoRepository.saveAll(newPedido.getItens());

    emailService.sendOrderConfirmationHtmlEmail(newPedido);

    return newPedido;
  }

  @Override
  public Page<Pedido> findPage(Integer page, Integer size, String orderBy, String direction) {
    UserSS user = UserServiceImpl.authenticated();

    if (user == null) {
      throw new AuthorizationException("Acesso negado");
    }

    Cliente cliente = clienteService.find(user.getId());
    PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

    return repository.findByCliente(cliente, pageRequest);
  }
}
