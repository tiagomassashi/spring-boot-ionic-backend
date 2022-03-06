package br.com.nagata.dev.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.nagata.dev.model.pk.ItemPedidoPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

  public double getSubTotal() {
    return (preco - desconto) * quantidade;
  }

  @JsonIgnore
  public Pedido getPedido() {
    return id.getPedido();
  }

  public void setPedido(Pedido pedido) {
    this.id.setPedido(pedido);
  }

  public Produto getProduto() {
    return id.getProduto();
  }

  public void setProduto(Produto produto) {
    this.id.setProduto(produto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ItemPedido other = (ItemPedido) obj;
    return Objects.equals(id, other.id);
  }
}
