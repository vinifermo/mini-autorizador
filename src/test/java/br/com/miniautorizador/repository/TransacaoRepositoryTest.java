package br.com.miniautorizador.repository;

import br.com.miniautorizador.domain.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    private Transacao transacao;

    @BeforeEach
    void setUp() {
        transacao = new Transacao();
        transacao.setNumeroCartao("123456789");
        transacao.setSenhaCartao("senha123");
        transacao.setValor(BigDecimal.valueOf(100.00));
        transacaoRepository.save(transacao);
    }

    @Test
    void testSaveTransacao() {
        Transacao savedTransacao = transacaoRepository.findById(transacao.getId()).orElse(null);

        assertNotNull(savedTransacao);
        assertEquals(transacao.getNumeroCartao(), savedTransacao.getNumeroCartao());
        assertEquals(transacao.getSenhaCartao(), savedTransacao.getSenhaCartao());
        assertEquals(transacao.getValor(), savedTransacao.getValor());
    }

    @Test
    void testFindById_TransacaoInexistente() {
        Transacao naoEncontrada = transacaoRepository.findById(999L).orElse(null);
        assertNull(naoEncontrada);
    }
}