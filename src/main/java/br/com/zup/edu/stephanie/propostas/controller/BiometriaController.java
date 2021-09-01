package br.com.zup.edu.stephanie.propostas.controller;

import br.com.zup.edu.stephanie.propostas.model.Biometria;
import br.com.zup.edu.stephanie.propostas.repository.BiometriaRepository;
import br.com.zup.edu.stephanie.propostas.repository.CartaoRepository;
import br.com.zup.edu.stephanie.propostas.request.BiometriaRequest;
import br.com.zup.edu.stephanie.propostas.service.Metrica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/biometria")
public class BiometriaController {

        private final BiometriaRepository biometriaRepository;
        private final CartaoRepository cartaoRepository;
        private final Metrica metricas;


        public BiometriaController(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository, Metrica metricas) {
            this.biometriaRepository = biometriaRepository;
            this.cartaoRepository = cartaoRepository;
            this.metricas = metricas;

        }

        @PostMapping("/{cartaoId}")
        public ResponseEntity<?> cadastrarBiometria(@RequestBody @Valid BiometriaRequest request, @PathVariable String cartaoId, UriComponentsBuilder uriBuilder) {
            if(!checarIntegridade(request.getFingerprint()))
                return ResponseEntity.badRequest().build();

            Optional<Biometria> biometriaObj = request.toModel(cartaoRepository, cartaoId);

            if(biometriaObj.isEmpty())
                return ResponseEntity.notFound().build();

            Biometria biometria = biometriaObj.get();
            biometriaRepository.save(biometria);
            metricas.incrementarBiometrias();

            URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }

        private boolean checarIntegridade(String valor) {
            try {
                Base64.getDecoder().decode(valor);
                return true;
            }catch (IllegalArgumentException e) {
                return false;
            }
        }
}
