package br.com.zup.edu.stephanie.propostas.response;

import br.com.zup.edu.stephanie.propostas.model.Proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private String nome;
    private String documento;
    private String email;
    private String endereco;
    private BigDecimal salario;

    public PropostaResponse(Proposta proposta) {
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();

    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }
    public BigDecimal getSalario() {
        return salario;
    }
}
