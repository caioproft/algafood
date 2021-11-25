package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cozinha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CozinhaServiceTest {

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    public void deveCriarCozinha() {

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);
        cozinha.setNome("Chinesa");
        Cozinha cozinhaCriada = cozinhaService.criar(cozinha);

        Assertions.assertEquals(cozinha.getNome(), cozinhaCriada.getNome());
        Assertions.assertEquals(cozinha.getId(), cozinhaCriada.getId());

    }
}
