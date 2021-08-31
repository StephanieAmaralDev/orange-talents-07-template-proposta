package br.com.zup.edu.stephanie.propostas.response;

import br.com.zup.edu.stephanie.propostas.enums.StatusBloqueio;
import br.com.zup.edu.stephanie.propostas.model.BloqueioCartao;
import br.com.zup.edu.stephanie.propostas.model.Cartao;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BloquearCartaoResponse {

    @NotBlank
    private StatusBloqueio resultado;

    public StatusBloqueio getResultado() {
        return resultado;
    }

    public BloqueioCartao toModel(String userAgent, List<String> listaIps, Cartao cartao) {
        String ipCliente = listaIps.get(0);
        return new BloqueioCartao(cartao, ipCliente, userAgent);
    }
}
