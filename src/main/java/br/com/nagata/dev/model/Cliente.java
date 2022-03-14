package br.com.nagata.dev.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.nagata.dev.model.dto.ClienteDTO;
import br.com.nagata.dev.model.enums.Perfil;
import br.com.nagata.dev.model.enums.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nome;

  @Column(unique = true)
  private String email;

  private String cpgOuCnpj;
  private TipoCliente tipo;

  @JsonIgnore private String senha;

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private List<Endereco> enderecos = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "TELEFONE")
  private Set<String> telefones = new HashSet<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERFIS")
  private Set<Perfil> perfis = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "cliente")
  private List<Pedido> pedidos = new ArrayList<>();

  public Cliente(ClienteDTO dto) {
    this.id = dto.getId();
    this.nome = dto.getNome();
    this.email = dto.getEmail();
  }

  public void addPerfil(Perfil perfil) {
    perfis.add(perfil);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Cliente other = (Cliente) obj;
    return Objects.equals(id, other.id);
  }
}
