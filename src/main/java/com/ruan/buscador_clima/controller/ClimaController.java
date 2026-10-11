package com.ruan.buscador_clima.controller;

import com.ruan.buscador_clima.dto.ClimaResponse;
import com.ruan.buscador_clima.dto.PrevisaoResponse;
import com.ruan.buscador_clima.service.ClimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClimaController {

    private final ClimaService service;

    @GetMapping("/clima")
    public ClimaResponse buscarClimaAtual(@RequestParam String cidade) {
        return service.buscarClimaAtual(cidade);
    }

    @GetMapping("/previsao")
    public PrevisaoResponse buscarPrevisao(@RequestParam String cidade, @RequestParam(defaultValue = "3") int dias) {
        return service.buscarPrevisao(cidade, dias);
    }
}
