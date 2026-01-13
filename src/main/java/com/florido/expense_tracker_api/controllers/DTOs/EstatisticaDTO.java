package com.florido.expense_tracker_api.controllers.DTOs;

import com.florido.expense_tracker_api.enums.CategoriaTransacao;

import java.util.Map;

public record EstatisticaDTO(
        Double totalEntradas,
        Double totalSaidas,
        Double saldo,
        Integer quantidadeTransacoes,
        Map<CategoriaTransacao, Double> gastosCategoria
){
}
