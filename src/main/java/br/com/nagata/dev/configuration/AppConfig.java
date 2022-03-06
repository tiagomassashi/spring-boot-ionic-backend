package br.com.nagata.dev.configuration;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import br.com.nagata.dev.service.EmailService;
import br.com.nagata.dev.service.impl.MockEmailServiceImpl;
import br.com.nagata.dev.service.impl.SmtpEmailServiceImpl;

@Configuration
public class AppConfig {

  private static final String PROFILE_LOCAL = "local";

  private Environment env;

  @Autowired
  public AppConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public EmailService emailService(MailSender mailSender, JavaMailSender javaMailSender) {
    if (Arrays.asList(env.getActiveProfiles()).contains(PROFILE_LOCAL)) {
      return new MockEmailServiceImpl();
    }
    return new SmtpEmailServiceImpl(mailSender, javaMailSender);
  }
}
