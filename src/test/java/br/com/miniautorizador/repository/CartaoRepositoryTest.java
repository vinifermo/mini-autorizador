package br.com.miniautorizador.repository;

import br.com.miniautorizador.domain.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartaoRepositoryTest {

    @Autowired
    private CartaoRepository cartaoRepository;

    private Cartao cartao;

    @BeforeEach
    void setUp() {
        cartao = new Cartao();
        cartao.setNumeroCartao("123456789");
        cartao.setSenha("senha123");
        cartao.setSaldo(BigDecimal.valueOf(500));
        cartaoRepository.save(cartao);
    }

    @Test
    void testFindByNumeroCartao_CartaoExistente() {
        Cartao encontrado = cartaoRepository.findByNumeroCartao("123456789");

        assertEquals(cartao.getNumeroCartao(), encontrado.getNumeroCartao());
        assertEquals(cartao.getSenha(), encontrado.getSenha());
        assertEquals(cartao.getSaldo(), encontrado.getSaldo());
    }

    @Test
    void testFindByNumeroCartao_CartaoInexistente() {
        Cartao naoEncontrado = cartaoRepository.findByNumeroCartao("987654321");

        assertNull(naoEncontrado);
    }
}