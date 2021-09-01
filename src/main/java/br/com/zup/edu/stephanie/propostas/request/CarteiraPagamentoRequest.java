package br.com.zup.edu.stephanie.propostas.request;

import br.com.zup.edu.stephanie.propostas.enums.GatwayPagamento;
import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.model.CarteiraPagamento;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraPagamentoRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private GatwayPagamento carteira;

    public String getEmail() {
        return email;
    }

    public GatwayPagamento getCarteira() {
        return carteira;
    }

    public CarteiraPagamento toModel(String idLegado, Cartao cartao) {
        return new CarteiraPagamento(idLegado, email, carteira, cartao);
    }
}
