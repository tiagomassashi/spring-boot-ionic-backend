package br.com.nagata.dev.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.nagata.dev.model.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

  @GetMapping
  public List<Categoria> listar() {

    Categoria cat1 = new Categoria(1, "Informática");
    Categoria cat2 = new Categoria(2, "Escritório");

    List<Categoria> lista = new ArrayList<>();
    lista.add(cat1);
    lista.add(cat2);

    return lista;
  }
}
