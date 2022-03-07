package br.com.nagata.dev.service.impl;

import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import br.com.nagata.dev.model.Cliente;
import br.com.nagata.dev.model.Pedido;
import br.com.nagata.dev.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEmailServiceImpl implements EmailService {

  @Value("${default.sender}")
  private String sender;
  @Autowired
  private TemplateEngine engine;
  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public void sendOrderConfirmationEmail(Pedido pedido) {
    try {
      SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
      sendEmail(message);
    } catch (RuntimeException e) {
      log.error("Erro ao enviar email");
    }
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

  protected String htmlFromTemplatePedido(Pedido pedido) {
    Context context = new Context();
    context.setVariable("pedido", pedido);
    return engine.process("email/confirmacaoPedido", context);
  }

  @Override
  public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
    MimeMessage message;
    try {
      message = prepareMimeMessageFromPedido(pedido);
      sendHtmlEmail(message);
    } catch (RuntimeException | MessagingException e) {
      sendOrderConfirmationEmail(pedido);
    }
  }

  protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(pedido.getCliente().getEmail());
    helper.setFrom(sender);
    helper.setSubject("Pedido confirmado! Código: " + pedido.getId());
    helper.setSentDate(new Date(System.currentTimeMillis()));
    helper.setText(htmlFromTemplatePedido(pedido), true);
    return message;
  }

  @Override
  public void sendNewPasswordEmail(Cliente cliente, String newPass) {
    try {
      SimpleMailMessage message = prepareNewPasswordEmail(cliente, newPass);
      sendEmail(message);
    } catch (RuntimeException e) {
      log.error("Erro ao enviar email com nova senha");
    }
  }

  protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(cliente.getEmail());
    message.setFrom(sender);
    message.setSubject("Solicitação de nova senha");
    message.setSentDate(new Date(System.currentTimeMillis()));
    message.setText("Nova senha: " + newPass);
    return message;
  }
}
