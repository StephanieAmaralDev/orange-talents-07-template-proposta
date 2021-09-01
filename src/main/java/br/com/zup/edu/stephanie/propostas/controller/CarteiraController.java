package br.com.zup.edu.stephanie.propostas.controller;

import br.com.zup.edu.stephanie.propostas.enums.StatusCarteira;
import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.model.CarteiraPagamento;
import br.com.zup.edu.stephanie.propostas.repository.CartaoRepository;
import br.com.zup.edu.stephanie.propostas.repository.CarteiraPagamentoRepository;
import br.com.zup.edu.stephanie.propostas.request.CarteiraPagamentoRequest;
import br.com.zup.edu.stephanie.propostas.request.ErrorFormatDTO;
import br.com.zup.edu.stephanie.propostas.response.CarteiraPagamentoResponse;
import br.com.zup.edu.stephanie.propostas.service.ConsultarCartaoService;
import br.com.zup.edu.stephanie.propostas.service.Metrica;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final Metrica metricas;
    private final CartaoRepository cartaoRepository;
    private final ConsultarCartaoService consultarCartaoService;
    private final CarteiraPagamentoRepository carteiraPagamentoRepository;

    public CarteiraController(CartaoRepository cartaoRepository, Metrica metricas, CarteiraPagamentoRepository carteiraPagamentoRepository, ConsultarCartaoService consultarCartaoService) {
        this.cartaoRepository = cartaoRepository;
        this.carteiraPagamentoRepository = carteiraPagamentoRepository;
        this.consultarCartaoService = consultarCartaoService;
        this.metricas = metricas;
    }

    @PostMapping("/cartoes/{id}")
    public ResponseEntity<?> cadastrarCarteira(@PathVariable String id, @RequestBody @Valid CarteiraPagamentoRequest carteiraPagamentoRequest, UriComponentsBuilder uriBuilder){
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(id);
        if(cartaoBanco.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<CarteiraPagamento> carteiraBanco = carteiraPagamentoRepository.findByCarteiraAndCartaoId(carteiraPagamentoRequest.getCarteira(), cartaoBanco.get().getId());
        if(carteiraBanco.isPresent())
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Cartão já associado"));

        try {
            CarteiraPagamentoResponse carteiraPagamentoResponse = consultarCartaoService.associarCarteira(id, carteiraPagamentoRequest);
            if(verificarAssociacaoCarteira(carteiraPagamentoResponse)){
                CarteiraPagamento carteiraPagamento = carteiraPagamentoRequest.toModel(carteiraPagamentoResponse.getId(), cartaoBanco.get());
                carteiraPagamentoRepository.save(carteiraPagamento);
                metricas.incrementarCarteirasAssosciadas();

            }
        }catch(FeignException e){
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Não foi possível associar o cartão à carteira "+carteiraPagamentoRequest.getCarteira()));
        }

        URI uri = uriBuilder.path("/carteiras/{id}").buildAndExpand(1L).toUri();
        return ResponseEntity.created(uri).build();
    }

    public boolean verificarAssociacaoCarteira(CarteiraPagamentoResponse response){
        return response.getResultado().equals(StatusCarteira.ASSOCIADA);
    }



}
