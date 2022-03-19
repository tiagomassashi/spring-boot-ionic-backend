package br.com.nagata.dev.service.impl;

import br.com.nagata.dev.model.Cidade;
import br.com.nagata.dev.repository.CidadeRepository;
import br.com.nagata.dev.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeServiceImpl implements CidadeService {

  private final CidadeRepository repository;

  @Autowired
  public CidadeServiceImpl(CidadeRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Cidade> findByEstado(Integer estadoId) {
    return repository.findCidades(estadoId);
  }
}
