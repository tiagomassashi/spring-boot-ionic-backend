package br.com.nagata.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.nagata.dev.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
