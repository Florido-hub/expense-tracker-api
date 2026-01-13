package com.florido.expense_tracker_api.controllers;

import com.florido.expense_tracker_api.controllers.DTOs.EstatisticaDTO;
import com.florido.expense_tracker_api.services.EstatisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticasController {

    private final EstatisticasService estatisticasService;

    @GetMapping()
    public ResponseEntity<Object> getEstatisticas() {
        EstatisticaDTO estatistica = estatisticasService.getEstatisticas();

        return ResponseEntity.ok(estatistica);
    }

    //inicio=2025-01-01&fim=2025-01-31
    @GetMapping("/filter")
    public ResponseEntity<Object> getEstatisticasData(
            @RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fim) {
        EstatisticaDTO estatisticasData = estatisticasService.getEstatisticasData(inicio, fim);

        return ResponseEntity.ok(estatisticasData);
    }
}
