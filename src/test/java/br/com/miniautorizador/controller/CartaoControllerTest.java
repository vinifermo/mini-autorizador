package br.com.miniautorizador.controller;

import br.com.miniautorizador.dto.CartaoCriadoDTO;
import br.com.miniautorizador.dto.CriarCartaoDTO;
import br.com.miniautorizador.exception.CartaoExistenteException;
import br.com.miniautorizador.exception.CartaoInexistenteException;
import br.com.miniautorizador.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

class CartaoControllerTest {

    @InjectMocks
    private CartaoController cartaoController;

    @Mock
    private CartaoService cartaoService;

    private CriarCartaoDTO criarCartaoDTO;
    private CartaoCriadoDTO cartaoCriadoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        criarCartaoDTO = CriarCartaoDTO.builder()
                .numeroCartao("123456789")
                .senha("senha123")
                .build();

        cartaoCriadoDTO = CartaoCriadoDTO.builder()
                .numeroCartao("123456789")
                .senha("senha123")
                .build();

    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testCadastrarCartao() {

        when(cartaoService.criarCartao(any(CriarCartaoDTO.class))).thenReturn(cartaoCriadoDTO);

        ResponseEntity<?> response = cartaoController.cadastrarCartao(criarCartaoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartaoCriadoDTO, response.getBody());
    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testCadastrarCartao_CartaoExistente() {
        CriarCartaoDTO criarCartaoDTO = new CriarCartaoDTO(new BigDecimal("500"), "123456789", "senha123");

        doThrow(new CartaoExistenteException("123456789", "senha123"))
                .when(cartaoService).criarCartao(any(CriarCartaoDTO.class));

        try {
            cartaoController.cadastrarCartao(criarCartaoDTO);
        } catch (CartaoExistenteException e) {
            assertEquals("123456789", e.getNumeroCartao());
            assertEquals("senha123", e.getSenha());
        }
    }

    @Test
    @WithMockUser(username = "dk", password = "1234", roles = "USER")
    void testConsultarSaldo() {
        String numeroCartao = "123456789";
        BigDecimal saldo = BigDecimal.valueOf(500);

        when(cartaoService.consultarSaldo(numeroCartao)).thenReturn(ResponseEntity.ok(saldo));

        ResponseEntity<?> response = cartaoController.consultarSaldo(numeroCartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());
    }

//    @Test
//    @WithMockUser(username = "dk", password = "1234", roles = "USER")
//    void testConsultarSaldo_CartaoInexistente() {
//        String numeroCartao = "123456789";
//
//        when(cartaoService.consultarSaldo(numeroCartao))
//                .thenThrow(new CartaoInexistenteException());
//
//        CartaoInexistenteException exception = assertThrows(CartaoInexistenteException.class, () -> {
//            cartaoController.consultarSaldo(numeroCartao);
//        }
//        );
//    }

}
