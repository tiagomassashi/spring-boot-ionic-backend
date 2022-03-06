package br.com.nagata.dev.service.impl;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockEmailServiceImpl extends AbstractEmailServiceImpl {

  @Override
  public void sendEmail(SimpleMailMessage message) {
    log.info("Simulando envio de email...");
    log.info(message.toString());
    log.info("Email enviado");
  }

  @Override
  public void sendHtmlEmail(MimeMessage message) {
    log.info("Simulando envio de email HTML...");
    log.info(message.toString());
    log.info("Email enviado");
  }
}
