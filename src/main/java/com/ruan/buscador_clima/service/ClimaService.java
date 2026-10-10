package com.ruan.buscador_clima.service;

import com.ruan.buscador_clima.client.OpenMeteorClient;
import com.ruan.buscador_clima.dto.ClimaResponse;
import com.ruan.buscador_clima.dto.ForecastResponse;
import com.ruan.buscador_clima.dto.GeocodingResponse;
import com.ruan.buscador_clima.exception.CidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClimaService {

    private final OpenMeteorClient openMeteorClient;

    public ClimaResponse buscarClimaAtual(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Nome da cidade inválida");
        }
        GeocodingResponse coordenadas = openMeteorClient.buscarCoordenadas(cidade);
        if (coordenadas == null || coordenadas.results() == null || coordenadas.results().isEmpty()) {
            throw new CidadeNaoEncontradaException("Cidade " + cidade + " não encontrada");
        }
        GeocodingResponse.Resultado resultado = coordenadas.results().getFirst();
        ForecastResponse clima = openMeteorClient.buscarClima(resultado.latitude(), resultado.longitude(), 1);

        return new ClimaResponse(resultado.name(),
                resultado.country(),
                clima.current().temperature2m(),
                clima.current().apparentTemperature(),
                clima.current().relativeHumidity2m(),
                clima.current().windSpeed10m(),
                descreverCondicao(clima.current().weatherCode()),
                LocalDateTime.now()
        );
    }

    private String descreverCondicao(int codigo) {
        return switch (codigo) {
            case 0 -> "Céu limpo";
            case 1 -> "Predominantemente limpo";
            case 2 -> "Parcialmente nublado";
            case 3 -> "Nublado";
            case 45, 48 -> "Neblina";
            case 51, 53, 55 -> "Garoa";
            case 56, 57 -> "Garoa congelante";
            case 61, 63, 65 -> "Chuva";
            case 66, 67 -> "Chuva congelante";
            case 71, 73, 75, 77 -> "Neve";
            case 80, 81, 82 -> "Pancadas de chuva";
            case 85, 86 -> "Pancadas de neve";
            case 95 -> "Tempestade";
            case 96, 99 -> "Tempestade com granizo";
            default -> "Condição desconhecida";
        };
    }
}
