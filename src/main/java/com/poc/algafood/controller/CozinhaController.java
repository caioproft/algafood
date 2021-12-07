package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Cozinha;
import com.poc.algafood.exception.EntidadeEmUsoException;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.EntidadeNaoCadastradaException;
import com.poc.algafood.service.CozinhaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController {

  private CozinhaService service;

  public CozinhaController(CozinhaService service) {
    this.service = service;
  }

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
  public ResponseEntity<?> criar(@RequestBody Cozinha cozinha) {
    try {

      Cozinha novaCozinha = service.criar(cozinha);
      return ResponseEntity.status(HttpStatus.CREATED).body(novaCozinha);

    } catch (EntidadeJaCadastradaException ex) {

      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Optional<Cozinha>> atualizar(
      @PathVariable Long id, @RequestBody Cozinha cozinha) {
    return new ResponseEntity<>(service.atualizar(id, cozinha), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {

    try {
      service.excluir(id);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoCadastradaException ex) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
