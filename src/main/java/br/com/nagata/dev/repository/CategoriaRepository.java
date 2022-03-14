package br.com.nagata.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.nagata.dev.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {}
