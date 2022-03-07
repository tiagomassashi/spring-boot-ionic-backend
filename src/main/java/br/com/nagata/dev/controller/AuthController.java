package br.com.nagata.dev.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.nagata.dev.security.JWTUtil;
import br.com.nagata.dev.security.UserSS;
import br.com.nagata.dev.service.impl.UserServiceImpl;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  private JWTUtil jwtUtil;

  public AuthController(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/refresh_token")
  public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
    UserSS user = UserServiceImpl.authenticated();
    String token = jwtUtil.generateToken(user.getUsername());
    response.addHeader("Authorization", "Bearer " + token);
    return ResponseEntity.noContent().build();
  }
}
