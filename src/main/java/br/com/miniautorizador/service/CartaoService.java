package br.com.miniautorizador.service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoCriadoDTO;
import br.com.miniautorizador.dto.CriarCartaoDTO;
import br.com.miniautorizador.exception.CartaoExistenteException;
import br.com.miniautorizador.exception.CartaoInexistenteException;
import br.com.miniautorizador.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoCriadoDTO criarCartao(CriarCartaoDTO criarCartaoDTO) {

        verificarCartao(criarCartaoDTO);

        Cartao novoCartao = new Cartao();
        novoCartao.setNumeroCartao(criarCartaoDTO.getNumeroCartao());
        novoCartao.setSenha(criarCartaoDTO.getSenha());
        novoCartao.setSaldo(BigDecimal.valueOf(500));

        Cartao cartaoSalvo = cartaoRepository.save(novoCartao);
        return new CartaoCriadoDTO(cartaoSalvo.getNumeroCartao(), cartaoSalvo.getSenha());
    }

    private void verificarCartao(CriarCartaoDTO criarCartaoDTO) {
        Optional.ofNullable(cartaoRepository.findByNumeroCartao(criarCartaoDTO.getNumeroCartao()))
                .ifPresent(cartao -> {
                    throw new CartaoExistenteException(cartao.getNumeroCartao(), cartao.getSenha());
                });
    }

    public ResponseEntity<BigDecimal> consultarSaldo(String numeroCartao) {
        return Optional.ofNullable(cartaoRepository.findByNumeroCartao(numeroCartao))
                .map(cartao -> ResponseEntity.ok(cartao.getSaldo()))
                .orElseThrow(() -> new CartaoInexistenteException());
    }

}
