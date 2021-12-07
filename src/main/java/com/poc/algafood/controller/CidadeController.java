package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Cidade;
import com.poc.algafood.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController {

  @Autowired private CidadeService cidadeService;

  @GetMapping
  public ResponseEntity<List<Cidade>> buscarTodas() {

    return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscarCidades());
  }
}
