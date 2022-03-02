package br.com.nagata.dev.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Categoria implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nome;
}
