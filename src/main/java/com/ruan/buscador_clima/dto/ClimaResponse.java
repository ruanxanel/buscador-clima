package com.ruan.buscador_clima.dto;


import java.time.LocalDateTime;

public record ClimaResponse(
    String cidade,
    String pais,
    double temperaturaCelsius,
    double sensacaoTermica,
    int umidade,
    double velocidadeVento,
    String condicao,
    LocalDateTime consultadoEm
) {}
