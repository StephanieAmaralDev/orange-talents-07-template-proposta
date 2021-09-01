package br.com.zup.edu.stephanie.propostas.controller;

import br.com.zup.edu.stephanie.propostas.enums.StatusBloqueio;
import br.com.zup.edu.stephanie.propostas.model.BloqueioCartao;
import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.repository.CartaoBloqueiaRepository;
import br.com.zup.edu.stephanie.propostas.repository.CartaoRepository;
import br.com.zup.edu.stephanie.propostas.request.BloquearCartaoRequest;
import br.com.zup.edu.stephanie.propostas.request.ErrorFormatDTO;
import br.com.zup.edu.stephanie.propostas.response.BloquearCartaoResponse;
import br.com.zup.edu.stephanie.propostas.service.ConsultarCartaoService;
import br.com.zup.edu.stephanie.propostas.service.Metrica;
import br.com.zup.edu.stephanie.propostas.validation.ApiErroException;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class BloqueioCartaoController {

    private final Metrica metricas;
    private final CartaoRepository cartaoRepository;
    private final ConsultarCartaoService consultarCartaoService;
    private  final CartaoBloqueiaRepository cartaoBloqueiaRepository;


    public BloqueioCartaoController(CartaoBloqueiaRepository cartaoBloqueiaRepository, Metrica metricas, ConsultarCartaoService consultarCartaoService, CartaoRepository cartaoRepository) {
        this.cartaoBloqueiaRepository = cartaoBloqueiaRepository;
        this.consultarCartaoService = consultarCartaoService;
        this.cartaoRepository = cartaoRepository;
        this.metricas = metricas;

    }

    @PostMapping("/cartoes/{id}/bloqueio")
    public ResponseEntity<?> bloquearCartao(@PathVariable String id, @RequestHeader("User-Agent") String userAgent, @RequestHeader("X-Forwarded-For") List<String> ipCliente) {
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(id);

        if(cartaoBanco.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<BloqueioCartao> bloqueioBanco = cartaoBloqueiaRepository.findByCartaoId(id);
        if(bloqueioBanco.isPresent())
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Cartão já bloqueado"));

        try{

            BloquearCartaoRequest bloqueioRequest = new BloquearCartaoRequest("Proposta");
            BloquearCartaoResponse bloqueioResponse = consultarCartaoService.bloquearCartao(id, bloqueioRequest);

            if(verificarBloqueio(bloqueioResponse)){
                BloqueioCartao bloqueio = bloqueioResponse.toModel(userAgent, ipCliente, cartaoBanco.get());
                cartaoBloqueiaRepository.save(bloqueio);
                metricas.incrementarBloqueiosRealizados();
            }
            else
                throw new ApiErroException(422, "Não foi possível realizar bloqueio", "request");
        }catch (FeignException e){
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Não foi possível realizar bloqueio"));
        }

        return ResponseEntity.ok().build();
    }

    private boolean verificarBloqueio(BloquearCartaoResponse response) {
        return response.getResultado().equals(StatusBloqueio.BLOQUEADO);
    }
}
