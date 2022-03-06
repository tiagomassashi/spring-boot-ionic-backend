package br.com.nagata.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class SmtpEmailServiceImpl extends AbstractEmailServiceImpl {

  private MailSender mailSender;

  @Autowired
  public SmtpEmailServiceImpl(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendEmail(SimpleMailMessage message) {
    log.info("Enviando email...");
    mailSender.send(message);
    log.info("Email enviado");
  }
}
