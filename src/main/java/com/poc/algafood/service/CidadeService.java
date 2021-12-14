package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cidade;
import com.poc.algafood.domain.model.Estado;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.InformacaoInvalidaException;
import com.poc.algafood.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    EstadoService estadoService;

    public List<Cidade> buscarCidades() {
        return cidadeRepository.findAll();
    }

    @Transactional
    public Cidade cadastrar(Cidade cidade) {
        Estado estado;

        if (cidade.getEstado().getNome() != null) {
            estado = estadoService.buscaEstadoOuLancaExcecao(cidade.getEstado().getNome());
        } else {
            throw new InformacaoInvalidaException(String.format("Para cadastrar uma cidade é preciso informar um estado"));
        }
        cidade.getEstado().setId(estado.getId());
        deveCadastarCidadeOuLancarExcecao(cidade);
        return cidadeRepository.save(cidade);
    }

    private void deveCadastarCidadeOuLancarExcecao(Cidade cidade) {

        boolean existeCidade = cidadeRepository.findByNomeAndEstadoId(cidade.getNome(), cidade.getEstado().getId()).isPresent();

        if (existeCidade) {
            throw new EntidadeJaCadastradaException(
                    String.format(
                            "Já existe uma cidade cadastrada com estas informações. Cidade: %s e Estado: %s",
                            cidade.getNome(), cidade.getEstado().getNome()));
        }
    }
}
