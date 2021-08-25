package br.com.zup.edu.stephanie.propostas.response;

import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.model.Proposta;
import br.com.zup.edu.stephanie.propostas.repository.PropostaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class DadosCartaoResponse {

    @NotBlank
    private String id;

    @NotBlank
    private String titular;

    @NotBlank
    private Long idProposta;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotNull
    private BigDecimal limite;

    public DadosCartaoResponse(Cartao cartao) {
        this.id = cartao.getId();
        this.titular = cartao.getTitular();
        this.idProposta = cartao.getProposta().getId();
        this.emitidoEm = cartao.getEmitidoEm();
        this.limite = cartao.getLimite();
    }

    @Deprecated
    public DadosCartaoResponse() {}

    public Cartao toModel(PropostaRepository propostaRepository){
        Optional<Proposta> propostaBanco = propostaRepository.findById(idProposta);

        return new Cartao(id, emitidoEm, titular, limite, propostaBanco.get());
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}
