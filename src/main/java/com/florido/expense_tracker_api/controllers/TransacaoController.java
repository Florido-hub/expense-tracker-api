package com.florido.expense_tracker_api.controllers;

import com.florido.expense_tracker_api.controllers.DTOs.TransacaoDTO;
import com.florido.expense_tracker_api.controllers.mappers.TransacaoMapper;
import com.florido.expense_tracker_api.models.Transacao;
import com.florido.expense_tracker_api.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController implements GenericController{

    private final TransacaoService transacaoService;
    private final TransacaoMapper transacaoMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid TransacaoDTO dto){
        Transacao transacao = transacaoMapper.toEntity(dto);
        transacaoService.salvar(transacao);

        URI location = gerarHandlerLocation(transacao.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Page<TransacaoDTO>> getTransacoes(Pageable pageable) {
        Page<Transacao> transacoes = transacaoService.getTransacoes(pageable);
        Page<TransacaoDTO> transacaoDTO = transacoes.map(transacaoMapper::toDTO);

        return ResponseEntity.ok(transacaoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(String id){
        return transacaoService.getById(UUID.fromString(id))
                .map(transacao -> {
                    transacaoMapper.toDTO(transacao);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        return transacaoService.getById(UUID.fromString(id))
                .map(transacao ->  {
                    transacaoService.delete(transacao);
                    return ResponseEntity.ok().build();
                }).orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
