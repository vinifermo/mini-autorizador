package br.com.miniautorizador.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseCartaoErrorTest {

    @Test
    void testResponseCartaoError() {
        String senhaEsperada = "senha123";
        String numeroCartaoEsperado = "123456789";

        ResponseCartaoError responseCartaoError = new ResponseCartaoError(senhaEsperada, numeroCartaoEsperado);

        assertEquals(senhaEsperada, responseCartaoError.getSenha());
        assertEquals(numeroCartaoEsperado, responseCartaoError.getNumeroCartao());
    }
}