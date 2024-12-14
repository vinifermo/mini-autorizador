package br.com.miniautorizador.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarCartaoDTO {

    private BigDecimal saldo;
    private String numeroCartao;
    private String senha;

}
