package com.florido.expense_tracker_api.services;

import com.florido.expense_tracker_api.controllers.DTOs.EstatisticaDTO;
import com.florido.expense_tracker_api.enums.CategoriaTransacao;
import com.florido.expense_tracker_api.enums.TipoTransacao;
import com.florido.expense_tracker_api.models.Transacao;
import com.florido.expense_tracker_api.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstatisticasService {

    private final TransacaoRepository transacaoRepository;

    public EstatisticaDTO calcularEstatisticasTransacoes(){
        List<Transacao> transacoes = transacaoRepository.findAll();

        double entradas = transacoes
                .stream()
                .filter(t -> t.getTipo() == TipoTransacao.ENTRADA)
                .mapToDouble(Transacao::getValor)
                .sum();

        double saidas = transacoes
                .stream()
                .filter(t -> t.getTipo() == TipoTransacao.SAIDA)
                .mapToDouble(Transacao::getValor)
                .sum();

        double saldo = entradas - saidas;

        Map<CategoriaTransacao, Double> gastosCategoria = transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.SAIDA)
                .collect(Collectors.groupingBy(
                        Transacao::getCategoria,
                        Collectors.summingDouble(Transacao::getValor)
                ));

        return new EstatisticaDTO(
                entradas,
                saidas,
                saldo,
                transacoes.size(),
                gastosCategoria
        );
    }
}
