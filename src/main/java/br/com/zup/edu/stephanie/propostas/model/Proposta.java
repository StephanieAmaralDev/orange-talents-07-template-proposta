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

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    private String numeroCartao;

    public Proposta() {
    }

    @Valid
    public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        Assert.hasLength(documento, "O número do documento deve ser preenchido");
        Assert.hasLength(email, "O e-mail deve ser preenchido");
        Assert.hasLength(nome, "O nome deve ser preenchido");
        Assert.hasLength(endereco, "O endereço deve ser preenchido");
        Assert.notNull(salario, "O valor do salário deve ser preenchido");

        this.statusProposta = StatusProposta.NAO_ELEGIVEL;
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
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

    public SolicitarAnaliseRequest analisarSolicitante(){
        return new SolicitarAnaliseRequest(documento, nome, id.toString());
    }

    public void atualizarStatus(StatusAnalise status) {
        this.statusProposta = status.toProposta();
    }

    public void associarCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

}