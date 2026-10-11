package com.ruan.buscador_clima.exception;

import com.ruan.buscador_clima.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> tratarCidade(CidadeNaoEncontradaException e) {
        ErroResponse corpo = new ErroResponse(404,
                "Cidade não encontrada",
                e.getMessage(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
    }

    @ExceptionHandler(CidadeInvalidaException.class)
    public ResponseEntity<ErroResponse> tratarDigitacao(CidadeInvalidaException e) {
        ErroResponse corpo = new ErroResponse(400,
                "Nome da cidade inválido",
                e.getMessage(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResponse> tratarCampo(MissingServletRequestParameterException e) {
        ErroResponse corpo = new ErroResponse(400,
                "Parâmetro obrigatório ausente",
                "O parâmetro '" + e.getParameterName() + "' é obrigatório",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler(DiaInvalidoException.class)
    public ResponseEntity<ErroResponse> tratarDias(DiaInvalidoException e) {
        ErroResponse corpo = new ErroResponse(400,
                "Quantidade de dias inválida",
                e.getMessage(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> tratarTipoNumero(MethodArgumentTypeMismatchException e) {
        ErroResponse corpo = new ErroResponse(400,
                "Parâmetro com tipo inválido",
                "O parâmetro '" + e.getName() + "' recebeu '" + e.getValue() + "', mas precisa ser um número",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }
}
