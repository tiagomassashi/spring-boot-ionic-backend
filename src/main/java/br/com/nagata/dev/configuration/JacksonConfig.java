package br.com.nagata.dev.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.nagata.dev.model.PagamentoComBoleto;
import br.com.nagata.dev.model.PagamentoComCartao;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder() {
      return new Jackson2ObjectMapperBuilder() {
        public void configure(ObjectMapper objectMapper) {
          objectMapper.registerSubtypes(PagamentoComCartao.class);
          objectMapper.registerSubtypes(PagamentoComBoleto.class);
          super.configure(objectMapper);
        }
      };
  }
}
