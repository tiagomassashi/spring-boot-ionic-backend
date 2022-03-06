package br.com.nagata.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.nagata.dev.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
