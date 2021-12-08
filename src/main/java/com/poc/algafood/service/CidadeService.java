package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cidade;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

  @Autowired private CidadeRepository cidadeRepository;

  public List<Cidade> buscarCidades() {
    return cidadeRepository.findAll();
  }

  @Transactional
  public Cidade cadastrar(Cidade cidade) {

    Long estadoId = cidade.getEstado().getId();
    if (estadoId == null) {
      existeEstadoCadastrado();
    }
    deveCadastarCidadeOuLancarExcecao(cidade);
    return cidadeRepository.save(cidade);
  }

  private void existeEstadoCadastrado() {}

  private void deveCadastarCidadeOuLancarExcecao(Cidade cidade) {

    Optional<Cidade> cidadeCadastrada = cidadeRepository.findByNome(cidade.getNome());

    if (cidadeCadastrada.isPresent()
        && cidade.getEstado().getNome().equalsIgnoreCase(cidade.getEstado().getNome())) {
      throw new EntidadeJaCadastradaException(
          String.format(
              "Já existe uma cidade cadastrada com estas informações. Cidade: %s e Estado: %s",
              cidade.getNome(), cidade.getEstado().getNome()));
    }
  }
}
