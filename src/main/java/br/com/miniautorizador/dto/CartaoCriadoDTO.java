package br.com.miniautorizador.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCriadoDTO {

    private String numeroCartao;

    private String senha;

}
