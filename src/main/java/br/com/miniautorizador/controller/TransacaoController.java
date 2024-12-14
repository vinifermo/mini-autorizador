package br.com.miniautorizador.controller;

import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> transacao(@RequestBody Transacao transacao) {
        transacaoService.criaTransacao(transacao);
        return ResponseEntity.ok("OK");
    }

}