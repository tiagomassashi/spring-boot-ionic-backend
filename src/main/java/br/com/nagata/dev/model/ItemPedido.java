package br.com.nagata.dev.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.nagata.dev.model.pk.ItemPedidoPK;
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
public class ItemPedido implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @EmbeddedId
  private ItemPedidoPK id = new ItemPedidoPK();
  private Double desconto;
  private Integer quantidade;
  private Double preco;

  @JsonIgnore
  public Pedido getPedido() {
    return id.getPedido();
  }

  public Produto getProduto() {
    return id.getProduto();
  }
}
