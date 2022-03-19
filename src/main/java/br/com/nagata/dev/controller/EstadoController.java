package br.com.nagata.dev.controller;

import br.com.nagata.dev.model.dto.CidadeDTO;
import br.com.nagata.dev.model.dto.EstadoDTO;
import br.com.nagata.dev.service.CidadeService;
import br.com.nagata.dev.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoController {

  private final EstadoService estadoService;
  private final CidadeService cidadeService;

  @Autowired
  public EstadoController(EstadoService service, CidadeService cidadeService) {
    this.estadoService = service;
    this.cidadeService = cidadeService;
  }

  @GetMapping
  public ResponseEntity<List<EstadoDTO>> findAll() {
    return ResponseEntity.ok()
        .body(
            estadoService.findAll().stream()
                .map(EstadoDTO::new)
                .collect(Collectors.toList()));
  }

  @GetMapping("/{estadoId}/cidades")
  public ResponseEntity<List<CidadeDTO>> findCidade(@PathVariable Integer estadoId) {
    return ResponseEntity.ok()
        .body(
            cidadeService.findByEstado(estadoId).stream()
                .map(CidadeDTO::new)
                .collect(Collectors.toList()));
  }
}
