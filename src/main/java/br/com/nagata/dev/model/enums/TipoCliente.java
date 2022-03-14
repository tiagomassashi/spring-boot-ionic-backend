package br.com.nagata.dev.model.enums;

import lombok.Getter;

@Getter
public enum TipoCliente {
  PESSOAFISICA(1, "Pessoa Física"),
  PESSOAJURIDICA(2, "Pessoa Jurídica");

  private final Integer codigo;
  private final String descricao;

  private TipoCliente(int codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }
}
