package br.com.nagata.dev.service.impl;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmtpEmailServiceImpl extends AbstractEmailServiceImpl {

  private final MailSender mailSender;
  private final JavaMailSender javaMailSender;

  @Autowired
  public SmtpEmailServiceImpl(MailSender mailSender, JavaMailSender javaMailSender) {
    this.mailSender = mailSender;
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendEmail(SimpleMailMessage message) {
    log.info("Enviando email...");
    mailSender.send(message);
    log.info("Email enviado");
  }

  @Override
  public void sendHtmlEmail(MimeMessage message) {
    log.info("Enviando email...");
    javaMailSender.send(message);
    log.info("Email enviado");
  }
}
