package br.com.nagata.dev.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import br.com.nagata.dev.service.EmailService;
import br.com.nagata.dev.service.impl.MockEmailServiceImpl;

@Profile("local")
@Configuration
public class TestConfig {

  @Bean
  public EmailService emailService() {
    return new MockEmailServiceImpl();
  }
}
