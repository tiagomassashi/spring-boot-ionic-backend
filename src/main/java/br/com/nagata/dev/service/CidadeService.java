package br.com.nagata.dev.service;

import br.com.nagata.dev.model.Cidade;

import java.util.List;

public interface CidadeService {

  List<Cidade> findByEstado(Integer estadoId);
}
