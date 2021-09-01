package br.com.zup.edu.stephanie.propostas.model;

import br.com.zup.edu.stephanie.propostas.enums.GatwayPagamento;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CarteiraPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String idLegado;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private GatwayPagamento carteira;

    @ManyToOne
    private Cartao cartao;

    private CarteiraPagamento() {}

    public CarteiraPagamento(String idLegado, String email, GatwayPagamento carteira, Cartao cartao) {
        this.idLegado = idLegado;
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
    }
}
