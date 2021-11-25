package com.poc.algafood.exception;

public class EntidadeNaoCadastradaException extends RuntimeException {

    public EntidadeNaoCadastradaException(String mensagem) {
        super(mensagem);
    }
}
