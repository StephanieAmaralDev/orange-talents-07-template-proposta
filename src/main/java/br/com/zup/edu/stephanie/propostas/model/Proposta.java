package br.com.zup.edu.stephanie.propostas.model;

import br.com.zup.edu.stephanie.propostas.enums.StatusAnalise;
import br.com.zup.edu.stephanie.propostas.enums.StatusProposta;
import br.com.zup.edu.stephanie.propostas.request.SolicitarAnaliseRequest;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String documento;

    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @NotNull
    private BigDecimal salario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    private Proposta() {}

    @Valid
    public Proposta(String documento, @NotBlank String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        Assert.notNull(documento, "O número do documento deve ser preenchido");
        Assert.hasLength(email, "O e-mail deve ser preenchido");
        Assert.hasLength(nome, "O nome deve ser preenchido");
        Assert.hasLength(endereco, "O endereço deve ser preenchido");
        Assert.notNull(salario, "O valor do salário deve ser preenchido");

        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusProposta = StatusProposta.NAO_ELEGIVEL;
    }

    public Long getId() {
        return id;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void atualizarStatus(StatusAnalise status) {
        this.statusProposta = status.toProposta();
    }

    public SolicitarAnaliseRequest analisarSolicitante(){
        return new SolicitarAnaliseRequest(documento, nome, id.toString());
    }

    public void atrelaCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}