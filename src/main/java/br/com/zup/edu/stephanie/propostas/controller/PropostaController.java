package br.com.zup.edu.stephanie.propostas.controller;

import br.com.zup.edu.stephanie.propostas.model.Proposta;
import br.com.zup.edu.stephanie.propostas.repository.PropostaRepository;
import br.com.zup.edu.stephanie.propostas.request.PropostaRequest;
import br.com.zup.edu.stephanie.propostas.response.SolicitarAnaliseResponse;
import br.com.zup.edu.stephanie.propostas.service.AnaliseSolicitanteService;
import br.com.zup.edu.stephanie.propostas.validation.ApiErroException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final AnaliseSolicitanteService analiseSolicitanteService;
    private final PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository, AnaliseSolicitanteService analiseSolicitanteService) {
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
            propostaRepository.save(proposta);
        }
        catch (FeignException e) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "Erro ao analisar solicitante", "analise_solicitante");
        }

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
