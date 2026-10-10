package com.ruan.buscador_clima.dto;

import java.time.LocalDateTime;

public record ErroResponse(
     int status,
     String titulo,
     String mensagem,
     LocalDateTime timestamp
) {};
