package com.ruan.buscador_clima.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingResponse(List<Resultado> results) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Resultado(
            String name,
            double latitude,
            double longitude,
            String country,
            String admin1
    ) {}
}
