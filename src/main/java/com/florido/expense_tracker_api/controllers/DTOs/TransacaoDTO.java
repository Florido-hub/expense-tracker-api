package com.florido.expense_tracker_api.controllers.DTOs;

import com.florido.expense_tracker_api.enums.CategoriaTransacao;
import com.florido.expense_tracker_api.enums.TipoTransacao;
import com.florido.expense_tracker_api.models.Transacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record TransacaoDTO(

        UUID id,

        @Positive
        Long valor,

        @NotNull(message = "Escolha um tipo válido(ENTRADA/SAIDA")
        TipoTransacao tipo,

        @NotNull(message = "Categoria invalida!")
        CategoriaTransacao categoria,

        @NotNull(message = "Campo obrigatorio")
        LocalDate data
) {
    public Transacao mapearParaTransacao(){
        Transacao transacao = new Transacao();
        transacao.setValor(this.valor);
        transacao.setTipo(this.tipo);
        transacao.setCategoria(this.categoria);
        transacao.setData(this.data);
        return transacao;
    }
}
