package br.com.zup.edu.stephanie.propostas.controller;

import br.com.zup.edu.stephanie.propostas.model.Proposta;
import br.com.zup.edu.stephanie.propostas.repository.PropostaRepository;
import br.com.zup.edu.stephanie.propostas.request.PropostaRequest;
import br.com.zup.edu.stephanie.propostas.response.PropostaResponse;
import br.com.zup.edu.stephanie.propostas.response.SolicitarAnaliseResponse;
import br.com.zup.edu.stephanie.propostas.service.AnaliseSolicitanteService;
import br.com.zup.edu.stephanie.propostas.service.Metrica;
import br.com.zup.edu.stephanie.propostas.validation.ApiErroException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final AnaliseSolicitanteService analiseSolicitanteService;
    private final PropostaRepository propostaRepository;
    private Metrica metricas;

    public PropostaController(PropostaRepository propostaRepository, Metrica metricas, AnaliseSolicitanteService analiseSolicitanteService) {
        this.propostaRepository = propostaRepository;
        this.analiseSolicitanteService = analiseSolicitanteService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> verificaProposta = propostaRepository.findByDocumento(request.getDocumento());

        if(verificaProposta.isPresent())
            return ResponseEntity.status(422).build();

        Proposta proposta = request.toModel();

        propostaRepository.save(proposta);

        try {
            SolicitarAnaliseResponse analise = analiseSolicitanteService.solicitarAnalise(proposta.analisarSolicitante());
            proposta.atualizarStatus(analise.getResultadoSolicitacao());
            propostaRepository.save(proposta);
        }
        catch (FeignException e) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "Erro ao analisar solicitante", "analise_solicitante");
        }

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> statusProposta(@PathVariable Long id) {
        Optional<Proposta> propostaBanco = propostaRepository.findById(id);

        if (propostaBanco.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new PropostaResponse(propostaBanco.get()));
    }
}
