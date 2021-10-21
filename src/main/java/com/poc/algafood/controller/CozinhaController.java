package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Cozinha;
import com.poc.algafood.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController {

  @Autowired private CozinhaRepository repository;

  @GetMapping
  public ResponseEntity<List<Cozinha>> listar() {
    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
  }
}
