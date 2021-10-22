package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Cozinha;
import com.poc.algafood.repository.CozinhaRepository;
import com.poc.algafood.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController {

  @Autowired private CozinhaService service;

  @GetMapping
  public ResponseEntity<List<Cozinha>> listarTodos() {
    return new ResponseEntity<>(service.buscarTodas(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Cozinha>> listarUm(@PathVariable Long id) {
    Optional<Cozinha> cozinha = service.buscarUma(id);

    return cozinha.isPresent() ? ResponseEntity.ok(cozinha) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Cozinha> criaCozinha(@RequestBody Cozinha cozinha) {
    return new ResponseEntity<>(service.criar(cozinha), HttpStatus.CREATED);
  }
}
