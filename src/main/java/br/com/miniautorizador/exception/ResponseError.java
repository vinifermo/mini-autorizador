package br.com.miniautorizador.exception;

import lombok.*;

@Getter
@Setter
public class ResponseError {

    private String mensagem;

    public ResponseError(String mensagem) {
        this.mensagem = mensagem;
    }
}

