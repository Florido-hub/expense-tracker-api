package com.florido.expense_tracker_api.services;

import com.florido.expense_tracker_api.controllers.DTOs.EstatisticaDTO;
import com.florido.expense_tracker_api.enums.CategoriaTransacao;
import com.florido.expense_tracker_api.enums.TipoTransacao;
import com.florido.expense_tracker_api.models.Transacao;
import com.florido.expense_tracker_api.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstatisticasService {

    private final TransacaoRepository transacaoRepository;

    public EstatisticaDTO getEstatisticas() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return calcularEstatisticas(transacoes);
    }

    public EstatisticaDTO getEstatisticasData(LocalDate inicio, LocalDate fim) {

        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("É necessário informar tanto a data inicial quanto a data final.");
        }

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data inicial não pode ser maior que a data final.");
        }

        List<Transacao> transacoes = transacaoRepository.findAll();

        List<Transacao> filtro = transacoes.stream()
                .filter(t -> !t.getData().isBefore(inicio) && !t.getData().isAfter(fim))
                .toList();

        return calcularEstatisticas(filtro);
    }

    public EstatisticaDTO calcularEstatisticas(List<Transacao> transacoes){

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
