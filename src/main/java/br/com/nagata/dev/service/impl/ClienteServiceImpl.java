package br.com.nagata.dev.service.impl;

import br.com.nagata.dev.exception.AuthorizationException;
import br.com.nagata.dev.exception.DataIntegrityException;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Cidade;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.Endereco;
import br.com.nagata.dev.model.dto.ClienteNewDTO;
import br.com.nagata.dev.model.enums.Perfil;
import br.com.nagata.dev.repository.CidadeRepository;
import br.com.nagata.dev.repository.ClienteRepository;
import br.com.nagata.dev.repository.EnderecoRepository;
import br.com.nagata.dev.security.UserSS;
import br.com.nagata.dev.service.ClienteService;
import br.com.nagata.dev.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository repository;
  private final CidadeRepository cidadeRepository;
  private final EnderecoRepository enderecoRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final S3Service s3Service;

  @Autowired
  public ClienteServiceImpl(
      ClienteRepository repository,
      CidadeRepository cidadeRepository,
      EnderecoRepository enderecoRepository,
      BCryptPasswordEncoder passwordEncoder,
      S3Service s3Service) {
    this.repository = repository;
    this.cidadeRepository = cidadeRepository;
    this.enderecoRepository = enderecoRepository;
    this.passwordEncoder = passwordEncoder;
    this.s3Service = s3Service;
  }

  @Override
  public Cliente find(Integer id) {
    UserSS user = UserServiceImpl.authenticated();

    if (user != null && !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
      throw new AuthorizationException("Acesso negado");
    }

    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ObjectNotFoundException(
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
    Set<Perfil> perfis = new HashSet<>();
    perfis.add(Perfil.CLIENTE);

    Cliente cliente =
        new Cliente(
            null,
            dto.getNome(),
            dto.getEmail(),
            dto.getCpfOuCnpj(),
            dto.getTipo(),
            passwordEncoder.encode(dto.getSenha()),
            null,
            null,
            perfis,
            null,
            null);

    Cidade cidade =
        cidadeRepository
            .findById(dto.getCidadeId())
            .orElseThrow(
                () ->
                    new ObjectNotFoundException("Cidade não encontrada ID: " + dto.getCidadeId()));

    Endereco endereco =
        new Endereco(
            null,
            dto.getLogradouro(),
            dto.getNumero(),
            dto.getComplemento(),
            dto.getBairro(),
            dto.getCep(),
            cliente,
            cidade);

    cliente.setEnderecos(List.of(endereco));

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

  @Override
  public URI uploadProfilePicture(MultipartFile multipartFile) {
    UserSS user = UserServiceImpl.authenticated();

    if (user == null) {
      throw new AuthorizationException("Acesso negado");
    }

    URI uri = s3Service.uploadFile(multipartFile);

    Cliente cliente =
        repository
            .findById(user.getId())
            .orElseThrow(
                () ->
                    new ObjectNotFoundException(
                        "Objeto não encontrado ID: "
                            + user.getId()
                            + ", Tipo: "
                            + Cliente.class.getName()));

    cliente.setImageUrl(uri.toString());
    repository.save(cliente);

    return uri;
  }
}
