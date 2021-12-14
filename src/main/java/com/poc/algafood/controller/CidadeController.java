package com.poc.algafood.controller;

import com.poc.algafood.domain.model.Cidade;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import com.poc.algafood.exception.InformacaoInvalidaException;
import com.poc.algafood.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> buscarTodas() {

        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscarCidades());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Cidade cidade) {

        try {
            cidadeService.cadastrar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeJaCadastradaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (InformacaoInvalidaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            throw ex;
        }
    }
}
