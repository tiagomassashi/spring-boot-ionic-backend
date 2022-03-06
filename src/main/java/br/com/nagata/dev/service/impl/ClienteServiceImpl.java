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
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.repository.ClienteRepository;
import br.com.nagata.dev.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

  private ClienteRepository repository;

  @Autowired
  public ClienteServiceImpl(ClienteRepository repository) {
    this.repository = repository;
  }

  @Override
  public Cliente find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado ID: " + id + ", Tipo: " + Cliente.class.getName()));
  }

  @Override
  public Cliente update(Cliente newCliente) {
    Cliente cliente = this.find(newCliente.getId());
    updateData(cliente, newCliente);
    return repository.save(cliente);
  }

  @Override
  public void delete(Integer id) {
    this.find(id);

    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
    }
  }

  @Override
  public List<Cliente> findAll() {
    return repository.findAll();
  }

  @Override
  public Page<Cliente> findPage(Integer page, Integer size, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
    return repository.findAll(pageRequest);
  }

  private void updateData(Cliente cliente, Cliente newCliente) {
    cliente.setNome(newCliente.getNome());
    cliente.setEmail(newCliente.getEmail());
  }
}
