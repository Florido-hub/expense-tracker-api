package com.florido.expense_tracker_api.controllers;

import com.florido.expense_tracker_api.controllers.DTOs.TransacaoDTO;
import com.florido.expense_tracker_api.models.Transacao;
import com.florido.expense_tracker_api.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController implements GenericController{

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid TransacaoDTO dto){
        var transacao = dto.mapearParaTransacao();
        transacaoService.salvar(transacao);

        URI location = gerarHandlerLocation(transacao.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> getTransacoes() {
        List<Transacao> transacoes = transacaoService.getTransacoes();

        List<TransacaoDTO> list = transacoes
                .stream()
                .map(x -> new TransacaoDTO(
                        x.getId(),
                        x.getValor(),
                        x.getTipo(),
                        x.getCategoria(),
                        x.getData())).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(String id){
        UUID idTransacao = UUID.fromString(id);
        Optional<Transacao> optionalTransacao = transacaoService.getById(idTransacao);
        if(optionalTransacao.isPresent()){
            Transacao transacao = optionalTransacao.get();
            TransacaoDTO transacaoDTO = new TransacaoDTO(
                    transacao.getId(),
                    transacao.getValor(),
                    transacao.getTipo(),
                    transacao.getCategoria(),
                    transacao.getData());
            return ResponseEntity.ok(transacaoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        UUID idTransacao = UUID.fromString(id);
        Optional<Transacao> optionalTransacao = transacaoService.getById(idTransacao);
        if(!optionalTransacao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        transacaoService.delete(optionalTransacao.get());
        return ResponseEntity.noContent().build();
    }
}
