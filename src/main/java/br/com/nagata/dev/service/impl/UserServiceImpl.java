package br.com.nagata.dev.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import br.com.nagata.dev.security.UserSS;

public class UserServiceImpl {

  public static UserSS authenticated() {
    try {
      return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      return null;
    }
  }
}
