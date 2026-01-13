package com.florido.expense_tracker_api.controllers;

import com.florido.expense_tracker_api.controllers.DTOs.EstatisticaDTO;
import com.florido.expense_tracker_api.services.EstatisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticasController {

    private final EstatisticasService estatisticasService;

    @GetMapping
    public ResponseEntity<Object> getEstatisticas() {
        EstatisticaDTO estatistica = estatisticasService.calcularEstatisticasTransacoes();

        return ResponseEntity.ok(estatistica);
    }
}
