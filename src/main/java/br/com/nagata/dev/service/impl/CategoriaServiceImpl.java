package br.com.nagata.dev.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.DataIntegrityException;
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
  public Categoria update(Categoria newCategoria) {
    Categoria categoria = this.find(newCategoria.getId());
    updateData(categoria, newCategoria);
    return repository.save(categoria);
  }

  @Override
  public void delete(Integer id) {
    this.find(id);

    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não é possível excluir uma Categoria que possui produtos");
    }
  }

  @Override
  public List<Categoria> findAll() {
    return repository.findAll();
  }

  @Override
  public Page<Categoria> findPage(Integer page, Integer size, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
    return repository.findAll(pageRequest);
  }

  private void updateData(Categoria categoria, Categoria newCategoria) {
    categoria.setNome(newCategoria.getNome());
  }
}
