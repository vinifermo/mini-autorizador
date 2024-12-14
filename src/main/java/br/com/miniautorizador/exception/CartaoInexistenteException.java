package br.com.miniautorizador.exception;

public class CartaoInexistenteException extends RuntimeException {

    public CartaoInexistenteException(String mensagem) {
        super(mensagem);
    }

    public CartaoInexistenteException( ) {
        super();
    }

}
