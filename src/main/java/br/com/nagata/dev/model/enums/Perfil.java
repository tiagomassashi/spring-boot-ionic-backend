package br.com.nagata.dev.model.enums;

import lombok.Getter;

@Getter
public enum Perfil {
  ADMIN(1, "ROLE_ADMIN"), CLIENTE(2, "ROLE_CLIENTE");

  private Integer codigo;
  private String descricao;

  private Perfil(Integer codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }
}
