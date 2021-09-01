package br.com.zup.edu.stephanie.propostas.controller;

import br.com.zup.edu.stephanie.propostas.enums.StatusAvisoViagem;
import br.com.zup.edu.stephanie.propostas.model.AvisoViagem;
import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.repository.AvisoViagemRepository;
import br.com.zup.edu.stephanie.propostas.repository.CartaoRepository;
import br.com.zup.edu.stephanie.propostas.request.AvisoViagemRequest;
import br.com.zup.edu.stephanie.propostas.request.ErrorFormatDTO;
import br.com.zup.edu.stephanie.propostas.response.AvisoViagemResponse;
import br.com.zup.edu.stephanie.propostas.service.ConsultarCartaoService;
import br.com.zup.edu.stephanie.propostas.service.Metrica;
import br.com.zup.edu.stephanie.propostas.validation.ApiErroException;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;
    private final ConsultarCartaoService consultarCartaoService;
    private final AvisoViagemRepository avisoViagemRepository;
    private final Metrica metricas;

    public AvisoViagemController(CartaoRepository cartaoRepository, Metrica metricas, ConsultarCartaoService consultarCartaoService, AvisoViagemRepository avisoViagemRepository) {
        this.cartaoRepository = cartaoRepository;
        this.consultarCartaoService = consultarCartaoService;
        this.avisoViagemRepository = avisoViagemRepository;
        this.metricas = metricas;
    }


    @PostMapping("/cartoes/{id}/viagem")
    public ResponseEntity<?> notificarViagem(@PathVariable String id, @RequestBody @Valid AvisoViagemRequest avisoViagemRequest, HttpServletRequest request) {
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(id);
        if(cartaoBanco.isEmpty())
            return ResponseEntity.notFound().build();

        try{
            AvisoViagemResponse avisoViagemResponse = consultarCartaoService.notificarViagem(id, avisoViagemRequest);
            if(verificarAvisoViagem(avisoViagemResponse)){
                AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartaoBanco.get(), request);
                avisoViagemRepository.save(avisoViagem);
                metricas.incrementarAvisosViagem();

            }
            else
                throw new ApiErroException(422, "Não foi possível realizar a notificação de viagem", "request");
        }catch (FeignException e){
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Não foi possível realizar a notificação de viagem"));
        }

        return ResponseEntity.ok().build();
    }
    private boolean verificarAvisoViagem(AvisoViagemResponse response){
        return response.getResultado().equals(StatusAvisoViagem.CRIADO);
    }

}
