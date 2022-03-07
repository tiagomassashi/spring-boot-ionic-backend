package br.com.nagata.dev.service;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.Pedido;

public interface EmailService {

  void sendOrderConfirmationEmail(Pedido pedido);

  void sendEmail(SimpleMailMessage message);

  void sendOrderConfirmationHtmlEmail(Pedido pedido);

  void sendHtmlEmail(MimeMessage message);

  void sendNewPasswordEmail(Cliente cliente, String newPass);
}
