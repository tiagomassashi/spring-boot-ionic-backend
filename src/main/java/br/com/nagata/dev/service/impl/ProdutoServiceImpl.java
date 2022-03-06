package br.com.nagata.dev.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Categoria;
import br.com.nagata.dev.model.Produto;
import br.com.nagata.dev.repository.CategoriaRepository;
import br.com.nagata.dev.repository.ProdutoRepository;
import br.com.nagata.dev.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  private ProdutoRepository repository;
  private CategoriaRepository categoriaRepository;

  @Autowired
  public ProdutoServiceImpl(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
    this.repository = repository;
    this.categoriaRepository = categoriaRepository;
  }

  @Override
  public Produto find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado ID: " + id + ", Tipo: " + Produto.class.getName()));
  }

  @Override
  public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer size,
      String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
    List<Categoria> categorias = categoriaRepository.findAllById(ids);
    return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
  }
}
