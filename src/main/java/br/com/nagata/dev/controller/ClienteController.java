package br.com.nagata.dev.controller;

import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.dto.ClienteDTO;
import br.com.nagata.dev.model.dto.ClienteNewDTO;
import br.com.nagata.dev.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

  private final ClienteService service;

  @Autowired
  public ClienteController(ClienteService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.find(id));
  }

  @GetMapping("/email")
  public ResponseEntity<Cliente> find(@RequestParam(value = "value") String email) {
    return ResponseEntity.ok().body(service.findByEmail(email));
  }

  @PostMapping
  public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteDto) {
    Cliente cliente = service.insert(service.fromDTO(clienteDto));
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(cliente.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
      @Valid @RequestBody ClienteDTO clienteDto, @PathVariable Integer id) {
    clienteDto.setId(id);
    service.update(new Cliente(clienteDto));
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<ClienteDTO>> findAll() {
    return ResponseEntity.ok()
        .body(service.findAll().stream().map(ClienteDTO::new).collect(Collectors.toList()));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/page")
  public ResponseEntity<Page<ClienteDTO>> findPage(
      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "24", required = false) Integer size,
      @RequestParam(name = "orderBY", defaultValue = "nome", required = false) String orderBy,
      @RequestParam(name = "direction", defaultValue = "ASC", required = false) String direction) {
    return ResponseEntity.ok()
        .body(service.findPage(page, size, orderBy, direction).map(ClienteDTO::new));
  }

  @PostMapping("/picture")
  public ResponseEntity<Void> uploadProfilePicture(
      @RequestParam(name = "file") MultipartFile file) {
    URI uri = service.uploadProfilePicture(file);
    return ResponseEntity.created(uri).build();
  }
}
