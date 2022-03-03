package br.com.nagata.dev.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.nagata.dev.model.Categoria;
import br.com.nagata.dev.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

  private CategoriaService service;

  @Autowired
  public CategoriaController(CategoriaService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.buscar(id));
  }

  @PostMapping
  public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
    categoria = service.insert(categoria);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(categoria.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }
}
