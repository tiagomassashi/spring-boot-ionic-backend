package br.com.nagata.dev.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.nagata.dev.model.Pedido;
import br.com.nagata.dev.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

  private final PedidoService service;

  @Autowired
  public PedidoController(PedidoService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.find(id));
  }

  @PostMapping
  public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
    pedido = service.insert(pedido);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(pedido.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<Pedido>> findPage(
      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "24", required = false) Integer size,
      @RequestParam(name = "orderBY", defaultValue = "instante", required = false) String orderBy,
      @RequestParam(name = "direction", defaultValue = "DESC", required = false) String direction) {
    return ResponseEntity.ok().body(service.findPage(page, size, orderBy, direction));
  }
}
