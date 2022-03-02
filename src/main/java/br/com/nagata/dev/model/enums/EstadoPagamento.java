package br.com.nagata.dev.model.enums;

import lombok.Getter;

@Getter
public enum EstadoPagamento {
  PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), PARCELADO(3, "Parcelado");

  private Integer codigo;
  private String descricao;

  private EstadoPagamento(Integer codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }
}
