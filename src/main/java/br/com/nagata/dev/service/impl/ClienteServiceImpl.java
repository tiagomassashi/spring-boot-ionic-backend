package br.com.nagata.dev.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import br.com.nagata.dev.exception.DataIntegrityException;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Cidade;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.Endereco;
import br.com.nagata.dev.model.dto.ClienteNewDTO;
import br.com.nagata.dev.repository.CidadeRepository;
import br.com.nagata.dev.repository.ClienteRepository;
import br.com.nagata.dev.repository.EnderecoRepository;
import br.com.nagata.dev.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

  private ClienteRepository repository;
  private CidadeRepository cidadeRepository;
  private EnderecoRepository enderecoRepository;

  @Autowired
  public ClienteServiceImpl(ClienteRepository repository, CidadeRepository cidadeRepository,
      EnderecoRepository enderecoRepository) {
    this.repository = repository;
    this.cidadeRepository = cidadeRepository;
    this.enderecoRepository = enderecoRepository;
  }

  @Override
  public Cliente find(Integer id) {
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado ID: " + id + ", Tipo: " + Cliente.class.getName()));
  }

  @Transactional
  @Override
  public Cliente insert(Cliente cliente) {
    cliente.setId(null);
    cliente = repository.save(cliente);
    enderecoRepository.saveAll(cliente.getEnderecos());
    return cliente;
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
      throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
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

  @Override
  public Cliente fromDTO(ClienteNewDTO dto) {
    Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpgOuCnpj(),
        dto.getTipo(), null, null, null);

    Cidade cidade = cidadeRepository.findById(dto.getCidadeId()).orElseThrow(
        () -> new ObjectNotFoundException("Cidade não encontrada ID: " + dto.getCidadeId()));

    Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(),
        dto.getComplemento(), dto.getBairro(), dto.getCep(), cliente, cidade);

    cliente.setEnderecos(Arrays.asList(endereco));

    Set<String> telefones = new HashSet<String>();
    telefones.add(dto.getTelefone1());

    if (StringUtils.hasLength(dto.getTelefone2())) {
      telefones.add(dto.getTelefone2());
    }

    if (StringUtils.hasLength(dto.getTelefone3())) {
      telefones.add(dto.getTelefone3());
    }

    cliente.setTelefones(telefones);

    return cliente;
  }
}
