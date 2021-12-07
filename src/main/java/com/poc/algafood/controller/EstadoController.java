package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Estado;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.EntidadeNaoCadastradaException;
import com.poc.algafood.repository.EstadoRepository;
import com.poc.algafood.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/estados")
public class EstadoController {

  @Autowired private EstadoService estadoService;

  @GetMapping
  public ResponseEntity<?> listar() {
    try {

      return ResponseEntity.status(HttpStatus.OK).body(estadoService.buscarTodos());
    } catch (EntidadeNaoCadastradaException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarUmEstado(@PathVariable Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(estadoService.buscarUmEstado(id));
    } catch (EntidadeNaoCadastradaException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> criarEstado(@RequestBody Estado estado) {
    try {

      Estado novoEstado = estadoService.criarEstado(estado);
      return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
    } catch (EntidadeJaCadastradaException ex) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizarEstadoCompleto(
      @PathVariable Long id, @RequestBody Estado estado) {
    try {
      Estado estadoAtualizado = estadoService.atualizar(id, estado);
      return ResponseEntity.status(HttpStatus.OK).body(estadoAtualizado);

    } catch (EntidadeNaoCadastradaException ex) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
  }

  @PatchMapping("/{id")
  public ResponseEntity<?> atualiarEstadoParcialmente(
      @PathVariable Long id, @RequestBody Map<String, Object> estadoComAtualizacao) {
    try {

      Estado estadoAtualizado = estadoService.atualizarParcialment(id, estadoComAtualizacao);
      return ResponseEntity.status(HttpStatus.OK).body(estadoAtualizado);

    } catch (EntidadeNaoCadastradaException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> excluirEstado(@PathVariable Long id) {

    try {
      estadoService.excluir(id);
      return ResponseEntity.status(HttpStatus.OK).build();

    } catch (EntidadeNaoCadastradaException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
  }
}
