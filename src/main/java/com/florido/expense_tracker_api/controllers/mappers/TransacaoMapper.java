package com.florido.expense_tracker_api.controllers.mappers;

import com.florido.expense_tracker_api.controllers.DTOs.TransacaoDTO;
import com.florido.expense_tracker_api.models.Transacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    Transacao toEntity(TransacaoDTO transacaoDTO);

    TransacaoDTO toDTO(Transacao transacao);
}
