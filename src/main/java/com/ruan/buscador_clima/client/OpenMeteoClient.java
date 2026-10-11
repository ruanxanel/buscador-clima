package com.ruan.buscador_clima.client;

import com.ruan.buscador_clima.dto.ForecastResponse;
import com.ruan.buscador_clima.dto.GeocodingResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenMeteorClient {

    private final RestClient restClient = RestClient.create();


    public GeocodingResponse buscarCoordenadas(String cidade) {
        GeocodingResponse geo = restClient.get()
                .uri("https://geocoding-api.open-meteo.com/v1/search?name={cidade}&count=1&language=pt", cidade)
                .retrieve()
                .body(GeocodingResponse.class);
        return geo;
    }

    public ForecastResponse buscarClima(double latitude, double longitude, int forecastDays) {
        ForecastResponse forecast = restClient.get()
                .uri("https://api.open-meteo.com/v1/forecast?" +
                        "latitude={latitude}&longitude={longitude}&current=temperature_2m,apparent_temperature,relativ" +
                        "e_humidity_2m,wind_speed_10m,weather_code&daily=weather_code,temperature_2m_max,temp" +
                        "erature_2m_min&forecast_days={forecastDays}&timezone=auto", latitude, longitude, forecastDays)
                .retrieve()
                .body(ForecastResponse.class);
        return forecast;
    }
}
