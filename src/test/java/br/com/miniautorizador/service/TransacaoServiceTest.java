package br.com.miniautorizador.service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.exception.CartaoInexistenteException;
import br.com.miniautorizador.exception.SaldoInsuficienteException;
import br.com.miniautorizador.exception.SenhaInvalidaException;
import br.com.miniautorizador.repository.CartaoRepository;
import br.com.miniautorizador.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    private Transacao transacao;
    private Cartao cartao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cartao = new Cartao();
        cartao.setNumeroCartao("123456789");
        cartao.setSenha("senha123");
        cartao.setSaldo(BigDecimal.valueOf(500));

        transacao = new Transacao();
        transacao.setNumeroCartao("123456789");
        transacao.setSenhaCartao("senha123");
        transacao.setValor(BigDecimal.valueOf(100));


    }

    @Test
    void testCriaTransacao_ComSucesso() {
        String numeroCartao = "123456789";
        String senhaCartao = "senha123";
        BigDecimal valorTransacao = BigDecimal.valueOf(100);

        Transacao transacao = new Transacao(null, numeroCartao, senhaCartao, valorTransacao);
        Cartao cartao = new Cartao(1L, BigDecimal.valueOf(500), numeroCartao, senhaCartao);

        when(cartaoRepository.findByNumeroCartao(numeroCartao))
                .thenReturn(cartao);

        transacaoService.criaTransacao(transacao);

        verify(cartaoRepository, times(2)).findByNumeroCartao(numeroCartao);
        verify(transacaoRepository, times(1)).save(transacao);
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void testCriaTransacao_CartaoInexistente() {
        when(cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao()))
                .thenReturn(null);

        assertThrows(CartaoInexistenteException.class, () -> transacaoService.criaTransacao(transacao));
        verify(cartaoRepository, times(1)).findByNumeroCartao(transacao.getNumeroCartao());
        verifyNoInteractions(transacaoRepository);
    }

    @Test
    void testCriaTransacao_SenhaInvalida() {
        cartao.setSenha("senhaErrada");
        when(cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao()))
                .thenReturn(cartao);

        assertThrows(SenhaInvalidaException.class, () -> transacaoService.criaTransacao(transacao));
        verify(cartaoRepository, times(1)).findByNumeroCartao(transacao.getNumeroCartao());
        verifyNoInteractions(transacaoRepository);
    }

    @Test
    void testCriaTransacao_SaldoInsuficiente() {
        transacao.setValor(BigDecimal.valueOf(600));
        when(cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao()))
                .thenReturn(cartao);

        assertThrows(SaldoInsuficienteException.class, () -> transacaoService.criaTransacao(transacao));
        verify(cartaoRepository, times(1)).findByNumeroCartao(transacao.getNumeroCartao());
        verifyNoInteractions(transacaoRepository);
    }
}