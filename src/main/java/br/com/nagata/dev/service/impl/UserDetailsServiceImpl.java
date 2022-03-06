package br.com.nagata.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.repository.ClienteRepository;
import br.com.nagata.dev.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private ClienteRepository repository;

  @Autowired
  public UserDetailsServiceImpl(ClienteRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Cliente cliente =
        repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
  }
}
