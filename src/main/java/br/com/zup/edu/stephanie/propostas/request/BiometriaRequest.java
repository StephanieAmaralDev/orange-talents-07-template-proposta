package br.com.zup.edu.stephanie.propostas.request;

import br.com.zup.edu.stephanie.propostas.model.Biometria;
import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.repository.CartaoRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class BiometriaRequest {
    @NotBlank
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public Optional<Biometria> toModel(CartaoRepository cartaoRepository, String cartaoId) {
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(cartaoId);

        if(cartaoBanco.isPresent()){
            Cartao cartao = cartaoBanco.get();
            Biometria biometria = new Biometria(this.fingerprint, cartao);

            return Optional.of(biometria);
        }

        return Optional.empty();
    }
}
