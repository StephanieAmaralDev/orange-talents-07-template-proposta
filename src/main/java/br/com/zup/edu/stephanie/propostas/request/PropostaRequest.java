package br.com.zup.edu.stephanie.propostas.request;

import br.com.zup.edu.stephanie.propostas.model.DocumentoLimpo;
import br.com.zup.edu.stephanie.propostas.model.Proposta;
import br.com.zup.edu.stephanie.propostas.validation.CPFCNPJValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

    @CPFCNPJValid
    @NotBlank
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

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

    public Proposta toModel() {
        return new Proposta(new DocumentoLimpo(documento), email, nome, endereco, salario);
    }
}
