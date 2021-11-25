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

    public Optional<Cozinha> atualizar(Long id, Cozinha cozinha) {
        Optional<Cozinha> cozinhaExistente = repository.findById(id);

        if (cozinhaExistente.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaExistente, "id");
            repository.save(cozinhaExistente.get());
            return cozinhaExistente;
        }

        throw new EntidadeNaoCadastradaException(String.format("Cozinha de ID = %d não cadastrada no sistema", id));
    }

    public void excluir(Long id) {

        try {
            Optional<Cozinha> cozinha = repository.findById(id);

            if (cozinha.isPresent()) {
                repository.delete(cozinha.get());
            } else {
                throw new EntidadeNaoCadastradaException("Cozinha não cadastrada");
            }
        } catch (DataIntegrityViolationException ex) {

            throw new EntidadeEmUsoException(
                    String.format(
                            "A cozinha de código %d não pode ser excluída, pois está em uso por algum restaurante",
                            id));
        }
    }

    private boolean verificaSeJaExisteCozinhaPorNome(Cozinha cozinha) {
        Optional<Cozinha> resultado = repository.findByNome(cozinha.getNome());
        return resultado.isPresent();
    }
}
