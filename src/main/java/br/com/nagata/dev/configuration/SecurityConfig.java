package br.com.nagata.dev.configuration;

import br.com.nagata.dev.security.JWTAuthenticationFilter;
import br.com.nagata.dev.security.JWTAuthorizationFilter;
import br.com.nagata.dev.security.JWTUtil;
import br.com.nagata.dev.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String PROFILE_LOCAL = "local";
  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
  private static final String[] PUBLIC_MATCHERS_GET = {"/produtos/**", "/categorias/**", "/estados/**"};
  private static final String[] PUBLIC_MATCHERS_POST = {"/clientes", "/auth/forgot/**"};

  private final Environment env;
  private final UserDetailsServiceImpl userDetailsService;
  private final JWTUtil jwtUtil;

  @Autowired
  public SecurityConfig(
      Environment env, UserDetailsServiceImpl userDetailsService, JWTUtil jwtUtil) {
    this.env = env;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (Arrays.asList(env.getActiveProfiles()).contains(PROFILE_LOCAL)) {
      http.headers().frameOptions().disable();
    }
    http.cors().and().csrf().disable();
    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
        .permitAll()
        .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET)
        .permitAll()
        .antMatchers(PUBLIC_MATCHERS)
        .permitAll()
        .anyRequest()
        .authenticated();
    http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
    http.addFilter(
        new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    super.configure(auth);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
    config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return (CorsConfigurationSource) source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
