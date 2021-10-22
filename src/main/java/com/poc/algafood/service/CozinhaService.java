package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cozinha;
import com.poc.algafood.repository.CozinhaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {

  private CozinhaRepository repository;

  public CozinhaService(CozinhaRepository repository) {
    this.repository = repository;
  }

  public List<Cozinha> buscarTodas() {
    return repository.findAll();
  }

  public Optional<Cozinha> buscarUma(Long id) {
    return repository.findById(id);
  }

  public Cozinha criar(Cozinha cozinha) {

    if (verificaSeJaExisteCozinha(cozinha)) {
      throw new RuntimeException("Cozinha já cadastrada no sistema");
    } else {
      return repository.save(cozinha);
    }
  }

  private boolean verificaSeJaExisteCozinha(Cozinha cozinha) {
    Optional<Cozinha> resultado = repository.findByNome(cozinha.getNome());
    return resultado.isPresent();
  }
}
