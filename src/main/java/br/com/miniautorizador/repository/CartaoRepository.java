package br.com.miniautorizador.repository;

import br.com.miniautorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Cartao findByNumeroCartao(String numeroCartao);

}
