package br.com.miniautorizador.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();

    @Test
    void handleSenhaInvalidaException() {
        SenhaInvalidaException exception = new SenhaInvalidaException("Senha inválida");
        ResponseError response = exceptionHandler.handle(exception);

        assertEquals("Senha inválida", response.getMensagem());
    }

    @Test
    void handleCartaoInexistenteException() {
        CartaoInexistenteException exception = new CartaoInexistenteException("Cartão não encontrado");
        ResponseEntity<ResponseError> response = exceptionHandler.handle(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Cartão não encontrado", response.getBody().getMensagem());
    }

    @Test
    void handleSaldoInsuficienteException() {
        SaldoInsuficienteException exception = new SaldoInsuficienteException("Saldo insuficiente");
        ResponseError response = exceptionHandler.handle(exception);

        assertEquals("Saldo insuficiente", response.getMensagem());
    }

    @Test
    void handleCartaoExistenteException() {
        CartaoExistenteException exception = new CartaoExistenteException("123456789", "senha123");
        ResponseCartaoError response = exceptionHandler.handle(exception);

        assertEquals("123456789", response.getNumeroCartao());
        assertEquals("senha123", response.getSenha());
    }
}