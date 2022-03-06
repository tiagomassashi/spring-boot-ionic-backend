package br.com.nagata.dev.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.nagata.dev.model.Categoria;
import br.com.nagata.dev.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

//  @Query("SELECT DISTINCT prod FROM Produto prod INNER JOIN prod.categorias cate WHERE prod.nome LIKE %:nome% AND cate IN :categorias")
//  Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
  
  @Transactional(readOnly = true)
  Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
