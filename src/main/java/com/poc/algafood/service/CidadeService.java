package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cidade;
import com.poc.algafood.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CidadeService {

  @Autowired private CidadeRepository cidadeRepository;

  public List<Cidade> buscarCidades() {
    return cidadeRepository.findAll();
  }
}
