package br.com.nagata.dev.service.impl;

import br.com.nagata.dev.model.Estado;
import br.com.nagata.dev.repository.EstadoRepository;
import br.com.nagata.dev.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoService {

  private final EstadoRepository repository;

  @Autowired
  public EstadoServiceImpl(EstadoRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Estado> findAll() {
    return repository.findAllByOrderByNome();
  }
}
