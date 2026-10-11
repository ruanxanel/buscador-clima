package com.ruan.buscador_clima.service;

import com.ruan.buscador_clima.client.OpenMeteorClient;
import com.ruan.buscador_clima.dto.*;
import com.ruan.buscador_clima.exception.CidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClimaService {

    private final OpenMeteorClient openMeteorClient;

    public ClimaResponse buscarClimaAtual(String cidade) {

        GeocodingResponse.Resultado resultado = localizarCidade(cidade);
        ForecastResponse clima = openMeteorClient.buscarClima(resultado.latitude(),
                resultado.longitude(),
                1
        );

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

    public PrevisaoResponse buscarPrevisao(String cidade, int dias) {

        if (dias < 1 || dias > 7) {
            throw new IllegalArgumentException("Número " + dias + " errado, deve ser de 1 a 7 dias");
        }
        GeocodingResponse.Resultado resultado = localizarCidade(cidade);
        ForecastResponse clima = openMeteorClient.buscarClima(resultado.latitude(),
                resultado.longitude(),
                dias
        );
        List<PrevisaoDiaResponse> previsaoDias = new ArrayList<>();

        for (int i = 0; i < clima.daily().time().size(); i++) {

            LocalDate dia = LocalDate.parse(clima.daily().time().get(i));
            double temperaturaMaxima = clima.daily().temperature2mMax().get(i);
            double temperaturaMinima = clima.daily().temperature2mMin().get(i);
            String condicao = descreverCondicao(clima.daily().weatherCode().get(i));

            PrevisaoDiaResponse previsaoDiaResponse = new PrevisaoDiaResponse(dia,
                    condicao,
                    temperaturaMaxima,
                    temperaturaMinima);
            previsaoDias.add(previsaoDiaResponse);
        }

        return new PrevisaoResponse(resultado.name(),
                resultado.country(),
                previsaoDias);
    }

    private GeocodingResponse.Resultado localizarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Nome da cidade inválida");
        }
        GeocodingResponse coordenadas = openMeteorClient.buscarCoordenadas(cidade);
        if (coordenadas == null || coordenadas.results() == null || coordenadas.results().isEmpty()) {
            throw new CidadeNaoEncontradaException("Cidade " + cidade + " não encontrada");
        }
        return coordenadas.results().getFirst();
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
