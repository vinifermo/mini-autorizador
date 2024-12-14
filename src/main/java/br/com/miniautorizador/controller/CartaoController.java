package br.com.miniautorizador.controller;

import br.com.miniautorizador.dto.CartaoCriadoDTO;
import br.com.miniautorizador.dto.CriarCartaoDTO;
import br.com.miniautorizador.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoCriadoDTO> cadastrarCartao(@RequestBody CriarCartaoDTO criarCartaoDTO) {
        CartaoCriadoDTO cartaoCriadoDTO = cartaoService.criarCartao(criarCartaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoCriadoDTO);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<?> consultarSaldo(@PathVariable String numeroCartao) {
        BigDecimal saldo = cartaoService.consultarSaldo(numeroCartao).getBody();
        return ResponseEntity.ok(saldo);
    }

}
