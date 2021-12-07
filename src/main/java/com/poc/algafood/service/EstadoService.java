package com.poc.algafood.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.algafood.domain.model.Estado;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.EntidadeNaoCadastradaException;
import com.poc.algafood.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class EstadoService {

  private final EstadoRepository estadoRepository;

  public EstadoService(EstadoRepository estadoRepository) {
    this.estadoRepository = estadoRepository;
  }

  public List<Estado> buscarTodos() {
    List<Estado> estados = estadoRepository.findAll();

    if (estados.isEmpty()) {
      throw new EntidadeNaoCadastradaException("Não existem estados cadastrados até o momento");
    }

    return estados;
  }

  public Optional<Estado> buscarUmEstado(Long id) {
    Optional<Estado> estado = estadoRepository.findById(id);

    if (estado.isPresent()) {
      return estado;
    }
    throw new EntidadeNaoCadastradaException(
        String.format("Não existe um estado cadastrado com o ID: %d", id));
  }

  public Estado criarEstado(Estado estado) {

    if (existeEstado(estado)) {
      throw new EntidadeJaCadastradaException(
          String.format("O estado %s já está cadastrado no sistema", estado.getNome()));
    }
    return estadoRepository.save(estado);
  }

  private boolean existeEstado(Estado estado) {
    Optional<Estado> estadoCadastrado = estadoRepository.findByNome(estado.getNome());
    return estadoCadastrado.isPresent();
  }

  public Estado atualizar(Long id, Estado estado) {
    Optional<Estado> estadoSalvo = estadoRepository.findById(id);

    if (estadoSalvo.isPresent()) {
      BeanUtils.copyProperties(estado, estadoSalvo, "id");
      estadoRepository.save(estadoSalvo.get());
      return estadoSalvo.get();
    }

    throw new EntidadeNaoCadastradaException(
        String.format("O estado de ID = %d não está cadastrado no sistema", id));
  }

  public Estado atualizarParcialment(Long id, Map<String, Object> estadoComAtualizacao) {
    Optional<Estado> estadoSalvo = estadoRepository.findById(id);

    if (estadoSalvo.isPresent()) {

      ObjectMapper objectMapper = new ObjectMapper();
      Estado estadoRecebido = objectMapper.convertValue(estadoComAtualizacao, Estado.class);

      estadoComAtualizacao.forEach(
          (nomePropriedade, valorPropriedade) -> {
            Field campo =
                ReflectionUtils.findField(
                    Estado.class, nomePropriedade); // identifica cada campo a ser atualizado
            campo.setAccessible(true); // torna o campo acessível por ser privado

            Object valorDoCampo =
                ReflectionUtils.getField(
                    campo, estadoRecebido); // busca o valor da propriedade no objeto enviado pela
            // requisição
            ReflectionUtils.setField(
                campo,
                estadoSalvo,
                valorDoCampo); // atualiza o valor da propriedade no objeto existente no banco
          });

      estadoRepository.save(estadoSalvo.get());
      return estadoSalvo.get();
    }

    throw new EntidadeNaoCadastradaException(
        String.format("O estado de ID = %d não está cadastrado no sistema", id));
  }

  public void excluir(Long id) {
    Optional<Estado> estadoSalvo = estadoRepository.findById(id);

    estadoSalvo.ifPresent(estadoRepository::delete);

    throw new EntidadeNaoCadastradaException(
        String.format("O estado de ID = %d não está cadastrado no sistema", id));
  }
}
