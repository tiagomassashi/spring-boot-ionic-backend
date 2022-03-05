package br.com.nagata.dev.service;

import java.util.List;
import org.springframework.data.domain.Page;
import br.com.nagata.dev.model.Categoria;

public interface CategoriaService {

  Categoria find(Integer id);

  Categoria insert(Categoria categoria);

  Categoria update(Categoria categoria);

  void delete(Integer id);

  List<Categoria> findAll();
  
  Page<Categoria> findPage(Integer page, Integer size, String orderBy, String direction);
}
