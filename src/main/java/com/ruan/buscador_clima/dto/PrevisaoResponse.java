package com.ruan.buscador_clima.dto;

import java.util.List;

public record PrevisaoResponse(
        String cidade,
        String pais,
        List<PrevisaoDiaResponse> dias
) {}
