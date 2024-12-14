package br.com.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseError handle(SenhaInvalidaException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(CartaoInexistenteException.class)
    public ResponseEntity<ResponseError> handle(CartaoInexistenteException ex) {
        return Optional.ofNullable(ex.getMessage())
                .map(msg -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(msg)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseError handle(SaldoInsuficienteException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseCartaoError handle(CartaoExistenteException ex) {
        return new ResponseCartaoError(ex.getSenha(), ex.getNumeroCartao());
    }

}

