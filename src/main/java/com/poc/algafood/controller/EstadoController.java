package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Estado;
import com.poc.algafood.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estados")
public class EstadoController {

  @Autowired private EstadoRepository repository;

  @GetMapping
  public ResponseEntity<List<Estado>> listar() {
    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
  }
}
