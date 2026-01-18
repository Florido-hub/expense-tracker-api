package com.florido.expense_tracker_api.services;

import com.florido.expense_tracker_api.models.Transacao;
import com.florido.expense_tracker_api.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public Transacao salvar(Transacao transacao){
        return transacaoRepository.save(transacao);
    }

    public Page<Transacao> getTransacoes(Pageable pageRequest){
        return transacaoRepository.findAll(pageRequest);
    }

    public Optional<Transacao> getById(UUID id){
        return transacaoRepository.findById(id);
    }

    public void delete(Transacao transacao){
        transacaoRepository.delete(transacao);
    }
}
