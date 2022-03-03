package br.com.nagata.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.nagata.dev.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

  private PedidoService service;

  @Autowired
  public PedidoController(PedidoService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.buscar(id));
  }
}
