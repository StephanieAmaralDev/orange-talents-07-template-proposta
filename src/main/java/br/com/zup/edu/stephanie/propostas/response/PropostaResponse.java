package br.com.zup.edu.stephanie.propostas.response;

import br.com.zup.edu.stephanie.propostas.enums.StatusProposta;
import br.com.zup.edu.stephanie.propostas.model.Proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private String nome;
    private String documento;
    private String email;
    private String endereco;
    private BigDecimal salario;
    private StatusProposta status;

    public PropostaResponse(Proposta proposta) {
        this.nome = proposta.getNome();
        this.status = proposta.getStatusProposta();
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

    public StatusProposta getStatus() {
        return status;
    }
    public String getEndereco() {
        return endereco;
    }
    public BigDecimal getSalario() {
        return salario;
    }

}
