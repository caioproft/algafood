package com.poc.algafood.service;

import com.poc.algafood.domain.model.Cozinha;
import com.poc.algafood.exception.EntidadeJaCadastradaException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CozinhaServiceTest {

  @Autowired private CozinhaService cozinhaService;

  @Test
  public void deveCriarCozinhaComSucesso() {

    Cozinha cozinha = new Cozinha();
    cozinha.setNome("Chinesa");
    Cozinha cozinhaCriada = cozinhaService.criar(cozinha);

    assertThat(cozinhaCriada).isNotNull();
    assertThat(cozinhaCriada.getId()).isNotNull();
    assertThat(cozinhaCriada.getNome()).isNotNull();
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void deveFalharAoCadastrarCozinhaSemNome() {

    Cozinha cozinha = new Cozinha();
    cozinha.setNome(null);
    cozinhaService.criar(cozinha);
  }

  @Test(expected = EntidadeJaCadastradaException.class)
  public void deveFalharAoCadastrarCozinhaComNomeJaCadastrado() {

    Cozinha cozinha = new Cozinha();
    cozinha.setNome("Brasileira");
    cozinhaService.criar(cozinha);

    Cozinha cozinhaComMesmoNome = new Cozinha();
    cozinhaComMesmoNome.setNome("Brasileira");
    cozinhaService.criar(cozinhaComMesmoNome);
  }
}
