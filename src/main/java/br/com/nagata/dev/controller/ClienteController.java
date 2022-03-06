package br.com.nagata.dev.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.dto.ClienteDTO;
import br.com.nagata.dev.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

  private ClienteService service;

  @Autowired
  public ClienteController(ClienteService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.find(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@RequestBody ClienteDTO clienteDto, @PathVariable Integer id) {
    clienteDto.setId(id);
    service.update(new Cliente(clienteDto));
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<ClienteDTO>> findAll() {
    return ResponseEntity.ok()
        .body(service.findAll().stream().map(ClienteDTO::new).collect(Collectors.toList()));
  }

  @GetMapping("/page")
  public ResponseEntity<Page<ClienteDTO>> findPage(
      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "24", required = false) Integer size,
      @RequestParam(name = "orderBY", defaultValue = "nome", required = false) String orderBy,
      @RequestParam(name = "direction", defaultValue = "ASC", required = false) String direction) {
    return ResponseEntity.ok()
        .body(service.findPage(page, size, orderBy, direction).map(ClienteDTO::new));
  }
}
