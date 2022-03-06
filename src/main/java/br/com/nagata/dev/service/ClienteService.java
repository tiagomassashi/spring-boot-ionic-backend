package br.com.nagata.dev.service;

import java.util.List;
import org.springframework.data.domain.Page;
import br.com.nagata.dev.model.Cliente;

public interface ClienteService {

  Cliente find(Integer id);

  Cliente update(Cliente cliente);

  void delete(Integer id);

  List<Cliente> findAll();
  
  Page<Cliente> findPage(Integer page, Integer size, String orderBy, String direction);
}
