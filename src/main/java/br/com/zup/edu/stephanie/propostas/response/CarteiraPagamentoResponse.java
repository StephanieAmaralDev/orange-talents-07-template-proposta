package br.com.zup.edu.stephanie.propostas.response;

import br.com.zup.edu.stephanie.propostas.enums.StatusCarteira;

public class CarteiraPagamentoResponse {

    private StatusCarteira resultado;
    private String id;

    public StatusCarteira getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
