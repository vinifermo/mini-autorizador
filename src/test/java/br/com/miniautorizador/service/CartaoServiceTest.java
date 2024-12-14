package br.com.miniautorizador.service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoCriadoDTO;
import br.com.miniautorizador.dto.CriarCartaoDTO;
import br.com.miniautorizador.exception.CartaoExistenteException;
import br.com.miniautorizador.exception.CartaoInexistenteException;
import br.com.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartaoServiceTest {

    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    private CriarCartaoDTO criarCartaoDTO;
//    private CartaoCriadoDTO cartaoCriadoDTO;
    private Cartao cartao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        criarCartaoDTO = CriarCartaoDTO.builder()
                .numeroCartao("123456789")
                .senha("senha123")
                .build();

//        cartaoCriadoDTO = CartaoCriadoDTO.builder()
//                .numeroCartao("123456789")
//                .senha("senha123")
//                .build();

        cartao = new Cartao();
        cartao.setNumeroCartao("123456789");
        cartao.setSenha("senha123");
        cartao.setSaldo(BigDecimal.valueOf(500));
    }

    @Test
    void testCriarCartao_Sucesso() {
        when(cartaoRepository.findByNumeroCartao(criarCartaoDTO.getNumeroCartao())).thenReturn(null);
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        CartaoCriadoDTO resultado = cartaoService.criarCartao(criarCartaoDTO);

        assertNotNull(resultado);
        assertEquals(criarCartaoDTO.getNumeroCartao(), resultado.getNumeroCartao());
        assertEquals(criarCartaoDTO.getSenha(), resultado.getSenha());
    }

    @Test
    void testCriarCartao_CartaoExistente() {
        when(cartaoRepository.findByNumeroCartao(criarCartaoDTO.getNumeroCartao())).thenReturn(cartao);

        CartaoExistenteException exception = assertThrows(CartaoExistenteException.class, () -> {
            cartaoService.criarCartao(criarCartaoDTO);
        });

        assertEquals("123456789", exception.getNumeroCartao());
    }

    @Test
    void testConsultarSaldo_Sucesso() {
        when(cartaoRepository.findByNumeroCartao("123456789")).thenReturn(cartao);

        ResponseEntity<BigDecimal> response = cartaoService.consultarSaldo("123456789");

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(500), response.getBody());
    }

    @Test
    void testConsultarSaldo_CartaoInexistente() {
        when(cartaoRepository.findByNumeroCartao("123456789")).thenReturn(null);

        assertThrows(CartaoInexistenteException.class, () -> {
            cartaoService.consultarSaldo("123456789");
        });
    }
}