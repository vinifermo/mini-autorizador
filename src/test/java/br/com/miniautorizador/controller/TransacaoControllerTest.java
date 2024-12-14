package br.com.miniautorizador.controller;

import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.exception.CartaoInexistenteException;
import br.com.miniautorizador.exception.SaldoInsuficienteException;
import br.com.miniautorizador.exception.SenhaInvalidaException;
import br.com.miniautorizador.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController transacaoController;

    @Mock
    private TransacaoService transacaoService;

    private Transacao transacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transacao = new Transacao();
        transacao.setNumeroCartao("123456789");
        transacao.setSenhaCartao("senha123");
        transacao.setValor(BigDecimal.valueOf(100));
    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testTransacao_Sucesso() {
        ResponseEntity<String> response = transacaoController.transacao(transacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testTransacao_CartaoInexistente() {
        doThrow(new CartaoInexistenteException("Cartão Inexistente"))
                .when(transacaoService).criaTransacao(transacao);

        CartaoInexistenteException exception = assertThrows(CartaoInexistenteException.class, () -> {
            transacaoController.transacao(transacao);
        });

        assertEquals("Cartão Inexistente", exception.getMessage());
    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testTransacao_SenhaInvalida() {
        doThrow(new SenhaInvalidaException("Senha Inválida"))
                .when(transacaoService).criaTransacao(transacao);

        SenhaInvalidaException exception = assertThrows(SenhaInvalidaException.class, () -> {
            transacaoController.transacao(transacao);
        });

        assertEquals("Senha Inválida", exception.getMessage());
    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testTransacao_SaldoInsuficiente() {
        doThrow(new SaldoInsuficienteException("Saldo Insuficiente"))
                .when(transacaoService).criaTransacao(transacao);

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            transacaoController.transacao(transacao);
        });

        assertEquals("Saldo Insuficiente", exception.getMessage());
    }
}