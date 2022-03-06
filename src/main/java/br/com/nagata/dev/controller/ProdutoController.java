package br.com.nagata.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.nagata.dev.controller.utils.URL;
import br.com.nagata.dev.model.Produto;
import br.com.nagata.dev.model.dto.ProdutoDTO;
import br.com.nagata.dev.service.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

  private ProdutoService service;

  @Autowired
  public ProdutoController(ProdutoService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produto> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.find(id));
  }

  @GetMapping
  public ResponseEntity<Page<ProdutoDTO>> findPage(
      @RequestParam(name = "nome", defaultValue = "", required = false) String nome,
      @RequestParam(name = "categorias", defaultValue = "", required = false) String categorias,
      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "24", required = false) Integer size,
      @RequestParam(name = "orderBY", defaultValue = "nome", required = false) String orderBy,
      @RequestParam(name = "direction", defaultValue = "ASC", required = false) String direction) {
    return ResponseEntity.ok().body(service.search(URL.decodeParam(nome),
        URL.decodeIntList(categorias), page, size, orderBy, direction).map(ProdutoDTO::new));
  }
}
