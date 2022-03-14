package br.com.nagata.dev.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.nagata.dev.model.Categoria;
import br.com.nagata.dev.model.dto.CategoriaDTO;
import br.com.nagata.dev.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

  private final CategoriaService service;

  @Autowired
  public CategoriaController(CategoriaService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Categoria> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.find(id));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDto) {
    Categoria categoria = service.insert(new Categoria(categoriaDto));
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(categoria.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
      @Valid @RequestBody CategoriaDTO categoriaDto, @PathVariable Integer id) {
    categoriaDto.setId(id);
    service.update(new Categoria(categoriaDto));
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<CategoriaDTO>> findAll() {
    return ResponseEntity.ok()
        .body(service.findAll().stream().map(CategoriaDTO::new).collect(Collectors.toList()));
  }

  @GetMapping("/page")
  public ResponseEntity<Page<CategoriaDTO>> findPage(
      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "24", required = false) Integer size,
      @RequestParam(name = "orderBY", defaultValue = "nome", required = false) String orderBy,
      @RequestParam(name = "direction", defaultValue = "ASC", required = false) String direction) {
    return ResponseEntity.ok()
        .body(service.findPage(page, size, orderBy, direction).map(CategoriaDTO::new));
  }
}
