package br.com.nagata.dev.service;

import org.springframework.mail.SimpleMailMessage;
import br.com.nagata.dev.model.Pedido;

public interface EmailService {

  void sendOrderConfirmationEmail(Pedido pedido);
  
  void sendEmail(SimpleMailMessage message);
}
