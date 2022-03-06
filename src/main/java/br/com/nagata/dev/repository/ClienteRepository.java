package br.com.nagata.dev.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.nagata.dev.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

  @Transactional(readOnly = true)
  Optional<Cliente> findByEmail(String email);
}
