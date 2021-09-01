package br.com.zup.edu.stephanie.propostas.request;

import br.com.zup.edu.stephanie.propostas.model.AvisoViagem;
import br.com.zup.edu.stephanie.propostas.model.Cartao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public AvisoViagem toModel(Cartao cartao, HttpServletRequest request) {
        String ipCliente = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        return new AvisoViagem(validoAte, destino, userAgent, ipCliente, cartao);
    }
}
