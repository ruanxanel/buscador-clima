package com.ruan.buscador_clima.dto;

import java.time.LocalDate;

public record PrevisaoDiaResponse (
    LocalDate data,
    String condicao,
    double temperaturaMaximo,
    double temperaturaMinima
){}
