package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cozinha;
import com.poc.algafood.exception.EntidadeEmUsoException;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.EntidadeNaoCadastradaException;
import com.poc.algafood.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {

  private CozinhaRepository repository;

  public CozinhaService(CozinhaRepository repository) {
    this.repository = repository;
  }

  private static String COZINHA_NÂO_ENCONTRADA = "Não existe uma cozinha cadastrada com o ID = %d";
  private static String COZINHA_EM_USO =
      "A cozinha de código %d não pode ser excluída, pois está em uso por algum restaurante";

  public List<Cozinha> buscarTodas() {
    return repository.findAll();
  }

  public Optional<Cozinha> buscarUma(Long id) {
    return repository.findById(id);
  }

  public Cozinha criar(Cozinha cozinha) {

    if (verificaSeJaExisteCozinhaPorNome(cozinha)) {
      throw new EntidadeJaCadastradaException(
          String.format("A cozinha %s já está cadastrada no sistema", cozinha.getNome()));
    } else {
      return repository.save(cozinha);
    }
  }

  public Cozinha atualizar(Long id, Cozinha cozinha) {
    Cozinha cozinhaExistente =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntidadeNaoCadastradaException(String.format(COZINHA_NÂO_ENCONTRADA, id)));

    BeanUtils.copyProperties(cozinha, cozinhaExistente, "id");
    repository.save(cozinhaExistente);
    return cozinhaExistente;
  }

  public void excluir(Long id) {

    try {
      Cozinha cozinha =
          repository
              .findById(id)
              .orElseThrow(
                  () ->
                      new EntidadeNaoCadastradaException(
                          String.format(COZINHA_NÂO_ENCONTRADA, id)));

      repository.delete(cozinha);

    } catch (DataIntegrityViolationException ex) {

      throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, id));
    }
  }

  private boolean verificaSeJaExisteCozinhaPorNome(Cozinha cozinha) {
    Optional<Cozinha> resultado = repository.findByNome(cozinha.getNome());
    return resultado.isPresent();
  }
}
