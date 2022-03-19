package br.com.nagata.dev.service;

import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Page;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.dto.ClienteNewDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ClienteService {

  Cliente find(Integer id);

  Cliente insert(Cliente cliente);

  Cliente update(Cliente cliente);

  void delete(Integer id);

  List<Cliente> findAll();

  Cliente findByEmail(String email);

  Page<Cliente> findPage(Integer page, Integer size, String orderBy, String direction);

  Cliente fromDTO(ClienteNewDTO dto);

  URI uploadProfilePicture(MultipartFile multipartFile);
}
