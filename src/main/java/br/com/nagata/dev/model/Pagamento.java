package br.com.nagata.dev.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import br.com.nagata.dev.model.enums.EstadoPagamento;
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
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Integer id;
  private EstadoPagamento estado;

  @OneToOne
  @JoinColumn(name = "pedido_id")
  @MapsId
  private Pedido pedido;
}
