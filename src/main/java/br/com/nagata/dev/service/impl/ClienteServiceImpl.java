package br.com.nagata.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
