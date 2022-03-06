package br.com.nagata.dev.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import br.com.nagata.dev.service.EmailService;
import br.com.nagata.dev.service.impl.SmtpEmailServiceImpl;

@Configuration
public class AppConfig {

  @Bean
  public EmailService emailService(MailSender mailSender) {
    return new SmtpEmailServiceImpl(mailSender);
  }
}
