package br.com.nagata.dev.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.nagata.dev.exception.ObjectNotFoundException;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.repository.ClienteRepository;
import br.com.nagata.dev.service.AuthService;
import br.com.nagata.dev.service.EmailService;
import net.bytebuddy.utility.RandomString;

@Service
public class AuthServiceImpl implements AuthService {

  private ClienteRepository clienteRepository;
  private BCryptPasswordEncoder passwordEncoder;
  private EmailService emailService;

  public AuthServiceImpl(ClienteRepository clienteRepository, BCryptPasswordEncoder passwordEncoder,
      EmailService emailService) {
    this.clienteRepository = clienteRepository;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
  }

  @Override
  public void sendNewPassword(String email) {
    Cliente cliente = clienteRepository.findByEmail(email)
        .orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));

    String newPass = newPassword();
    cliente.setSenha(passwordEncoder.encode(newPass));

    clienteRepository.save(cliente);

    emailService.sendNewPasswordEmail(cliente, newPass);
  }

  private String newPassword() {
    return RandomString.make(10);
  }
}
