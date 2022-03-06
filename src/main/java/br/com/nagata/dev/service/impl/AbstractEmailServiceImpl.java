package br.com.nagata.dev.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import br.com.nagata.dev.model.Pedido;
import br.com.nagata.dev.service.EmailService;

public abstract class AbstractEmailServiceImpl implements EmailService {

  @Value("${default.sender}")
  private String sender;

  @Override
  public void sendOrderConfirmationEmail(Pedido pedido) {
    SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
    sendEmail(message);
  }

  protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(pedido.getCliente().getEmail());
    message.setFrom(sender);
    message.setSubject("Pedido confirmado! Código: " + pedido.getId());
    message.setSentDate(new Date(System.currentTimeMillis()));
    message.setText(pedido.toString());
    return message;
  }
}
