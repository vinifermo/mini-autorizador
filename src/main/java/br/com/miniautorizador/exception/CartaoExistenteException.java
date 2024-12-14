package br.com.miniautorizador.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartaoExistenteException extends RuntimeException {

    private String numeroCartao;
    private String senha;

}
