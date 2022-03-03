package br.com.nagata.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Categoria;
import br.com.nagata.dev.repository.CategoriaRepository;
import br.com.nagata.dev.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

  private CategoriaRepository repository;

  @Autowired
  public CategoriaServiceImpl(CategoriaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Categoria find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado ID: " + id + ", Tipo: " + Categoria.class.getName()));
  }

  @Override
  public Categoria insert(Categoria categoria) {
    categoria.setId(null);
    return repository.save(categoria);
  }

  @Override
  public Categoria update(Categoria categoria) {
    this.find(categoria.getId());
    return repository.save(categoria);
  }
}
