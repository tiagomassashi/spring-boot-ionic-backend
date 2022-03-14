package br.com.nagata.dev.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.nagata.dev.model.dto.EmailDTO;
import br.com.nagata.dev.security.JWTUtil;
import br.com.nagata.dev.security.UserSS;
import br.com.nagata.dev.service.AuthService;
import br.com.nagata.dev.service.impl.UserServiceImpl;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  private final JWTUtil jwtUtil;
  private final AuthService service;

  public AuthController(JWTUtil jwtUtil, AuthService service) {
    this.jwtUtil = jwtUtil;
    this.service = service;
  }

  @PostMapping("/refresh_token")
  public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
    UserSS user = UserServiceImpl.authenticated();
    String token = jwtUtil.generateToken(user.getUsername());
    response.addHeader("Authorization", "Bearer " + token);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/forgot")
  public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDto) {
    service.sendNewPassword(emailDto.getEmail());
    return ResponseEntity.noContent().build();
  }
}
