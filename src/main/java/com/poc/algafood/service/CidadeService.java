package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cidade;
import com.poc.algafood.domain.model.Estado;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.EntidadeNaoCadastradaException;
import com.poc.algafood.exception.InformacaoInvalidaException;
import com.poc.algafood.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
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
    private EstadoService estadoService;

    private static String ESTADO_NAO_INFORMADO = "Para cadastrar uma cidade é preciso informar um estado";
    private static String CIDADE_NAO_CADASTRADA = "Não existe uma cidade cadastrada com este ID: %d";
    private static String CIDADE_JA_CADASTRADA = "Já existe uma cidade cadastrada com estas informações:\n Cidade: %s  Estado: %s";


    public List<Cidade> buscarCidades() {
        return cidadeRepository.findAll();
    }

    @Transactional
    public Cidade cadastrar(Cidade cidade) {
        Estado estado;

        if (cidade.getEstado().getNome().isEmpty()) {
            throw new InformacaoInvalidaException(String.format(ESTADO_NAO_INFORMADO));
        }
        estado = estadoService.buscaEstadoOuLancaExcecao(cidade.getEstado().getNome());
        cidade.getEstado().setId(estado.getId());
        deveCadastarCidadeOuLancarExcecao(cidade);
        return cidadeRepository.save(cidade);
    }

    public Cidade buscarUma(Long id) {
        Cidade cidadeSalva = buscaCidadeOuLancaExcecao(id);
        return cidadeSalva;
    }

    public void excluir(Long id) {
        Cidade cidadeSalva = buscaCidadeOuLancaExcecao(id);
        cidadeRepository.delete(cidadeSalva);
    }

    public Cidade atualizar(Long id, Cidade cidade) {

        Cidade cidadeCadastrada = cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoCadastradaException(String.format(CIDADE_NAO_CADASTRADA, id)));

        BeanUtils.copyProperties(cidade, cidadeCadastrada, "id");
        return cidadeRepository.save(cidadeCadastrada);
    }

    private Cidade buscaCidadeOuLancaExcecao(Long id) {
        return cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoCadastradaException(String.format(CIDADE_NAO_CADASTRADA, id)));
    }

    private void deveCadastarCidadeOuLancarExcecao(Cidade cidade) {

        boolean existeCidade = cidadeRepository.findByNomeAndEstadoId(cidade.getNome(), cidade.getEstado().getId()).isPresent();

        if (existeCidade) {
            throw new EntidadeJaCadastradaException(
                    String.format(CIDADE_JA_CADASTRADA, cidade.getNome(), cidade.getEstado().getNome()));
        }
    }
}
