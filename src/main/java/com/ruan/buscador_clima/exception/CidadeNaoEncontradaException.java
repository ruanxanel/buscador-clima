package com.ruan.buscador_clima.exception;

public class CidadeNaoEncontradaException extends RuntimeException {
    public CidadeNaoEncontradaException(String message) {
        super(message);
    }
}
