package br.com.nagata.dev.service;

import java.util.List;
import org.springframework.data.domain.Page;
import br.com.nagata.dev.model.Produto;

public interface ProdutoService {

  Produto find(Integer id);

  Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer size, String orderBy, String direction);
}
