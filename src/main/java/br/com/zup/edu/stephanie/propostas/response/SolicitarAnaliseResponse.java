package br.com.zup.edu.stephanie.propostas.response;

import br.com.zup.edu.stephanie.propostas.enums.StatusAnalise;

public class SolicitarAnaliseResponse {

    private String idProposta;
    private String documento;
    private String nome;
    private StatusAnalise resultadoSolicitacao;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public StatusAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
