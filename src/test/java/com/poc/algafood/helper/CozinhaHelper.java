package com.poc.algafood.helper;

import com.poc.algafood.domain.model.Cozinha;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class CozinhaHelper {

    public Cozinha criaCozinha(Long id, String nome){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(id);
        cozinha.setNome(nome);
        return cozinha;
    }
}
