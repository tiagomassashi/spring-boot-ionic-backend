package br.com.nagata.dev.service;

import br.com.nagata.dev.model.Categoria;

public interface CategoriaService {

  Categoria find(Integer id);

  Categoria insert(Categoria categoria);

  Categoria update(Categoria categoria);

  void delete(Integer id);
}
