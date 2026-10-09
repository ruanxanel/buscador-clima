package com.ruan.buscador_clima.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForecastResponse(Current current, Daily daily) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Current(
        @JsonProperty("temperature_2m")
        double temperature2m,
        @JsonProperty("apparent_temperature")
        double apparentTemperature,
        @JsonProperty("relative_humidity_2m")
        int relativeHumidity2m,
        @JsonProperty("wind_speed_10m")
        double windSpeed10m,
        @JsonProperty("weather_code")
        int weatherCode
    ){}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Daily (
        List<String> time,
        @JsonProperty("weather_code")
        List<Integer> weatherCode,
        @JsonProperty("temperature_2m_max")
        List<Double> temperature2mMax,
        @JsonProperty("temperature_2m_min")
        List<Double> temperature2mMin
    ){}
}