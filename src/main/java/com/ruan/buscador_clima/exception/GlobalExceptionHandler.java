package com.ruan.buscador_clima.exception;

import com.ruan.buscador_clima.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> tratarCidade(CidadeNaoEncontradaException e) {
        ErroResponse corpo = new ErroResponse(404, "Cidade não encontrada", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> tratarDigitacao(IllegalArgumentException e) {
        ErroResponse corpo = new ErroResponse(400, "Nome da cidade inválida", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResponse> tratarCampo(MissingServletRequestParameterException e) {
        ErroResponse corpo = new ErroResponse(400, "Campo de busca vazio", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }
}
