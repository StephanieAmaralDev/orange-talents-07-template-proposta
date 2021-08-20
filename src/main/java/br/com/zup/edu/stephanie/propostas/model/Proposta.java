package br.com.zup.edu.stephanie.propostas.model;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Proposta() {
    }

    @Valid
    public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        Assert.hasLength(documento, "O número do documento deve ser preenchido");
        Assert.hasLength(email, "O e-mail deve ser preenchido");
        Assert.hasLength(nome, "O nome deve ser preenchido");
        Assert.hasLength(endereco, "O endereço deve ser preenchido");
        Assert.notNull(salario, "O valor do salário deve ser preenchido");

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
}