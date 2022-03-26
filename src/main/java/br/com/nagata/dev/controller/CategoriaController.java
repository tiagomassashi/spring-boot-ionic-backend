package br.com.nagata.dev.controller;

import br.com.nagata.dev.model.Categoria;
import br.com.nagata.dev.model.dto.CategoriaDTO;
import br.com.nagata.dev.service.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

  private final CategoriaService service;

  @Autowired
  public CategoriaController(CategoriaService service) {
    this.service = service;
  }

  @ApiOperation("Busca por id")
  @GetMapping("/{id}")
  public ResponseEntity<Categoria> find(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.find(id));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @ApiOperation("Insere categoria")
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
  @ApiOperation("Atualiza categoria")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
      @Valid @RequestBody CategoriaDTO categoriaDto, @PathVariable Integer id) {
    categoriaDto.setId(id);
    service.update(new Categoria(categoriaDto));
    return ResponseEntity.noContent().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            code = 400,
            message = "Não é possível excluir uma categoria que possui produtos"),
        @ApiResponse(code = 404, message = "Código inexistente")
      })
  @PreAuthorize("hasAnyRole('ADMIN')")
  @ApiOperation("Deleta categoria")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Retona todas categorias")
  @GetMapping
  public ResponseEntity<List<CategoriaDTO>> findAll() {
    return ResponseEntity.ok()
        .body(service.findAll().stream().map(CategoriaDTO::new).collect(Collectors.toList()));
  }

  @ApiOperation("Retorna todas categorias com paginação")
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
