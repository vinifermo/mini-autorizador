package br.com.miniautorizador.service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.exception.CartaoInexistenteException;
import br.com.miniautorizador.exception.SaldoInsuficienteException;
import br.com.miniautorizador.exception.SenhaInvalidaException;
import br.com.miniautorizador.repository.CartaoRepository;
import br.com.miniautorizador.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CartaoRepository cartaoRepository;

    public void criaTransacao(Transacao transacao) {
         Optional.ofNullable(cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao()))
                .map(cartao -> verificarSenha(cartao, transacao.getSenhaCartao()))
                .map(cartao -> verificarSaldo(cartao, transacao.getValor()))
                .orElseThrow(() -> new CartaoInexistenteException("Cartão Inexistente"));

         realizarTransacao(transacao);
    }

    private Cartao verificarSenha(Cartao cartao, String senhaInformada) {
        return Optional.of(cartao)
                .filter(c -> c.getSenha().equals(senhaInformada))
                .orElseThrow(() -> new SenhaInvalidaException("Senha Invalida"));
    }

    private Cartao verificarSaldo(Cartao cartao, BigDecimal valorTransacao) {
        return Optional.of(cartao)
                .filter(c -> c.getSaldo().compareTo(valorTransacao) >= 0)
                .orElseThrow(() -> new SaldoInsuficienteException("Saldo Insuficiente"));
    }

    private void realizarTransacao(Transacao transacao) {
        Cartao cartao = cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao());
        BigDecimal saldoAtualizado = cartao.getSaldo().subtract(transacao.getValor());
        cartao.setSaldo(saldoAtualizado);
        transacaoRepository.save(transacao);
        cartaoRepository.save(cartao);
    }

}
