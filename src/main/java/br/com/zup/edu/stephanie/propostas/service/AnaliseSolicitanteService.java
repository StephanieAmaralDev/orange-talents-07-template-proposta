package br.com.zup.edu.stephanie.propostas.service;

import br.com.zup.edu.stephanie.propostas.request.SolicitarAnaliseRequest;
import br.com.zup.edu.stephanie.propostas.response.SolicitarAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="analiseSolicitante", url = "http://localhost:9999")
public interface AnaliseSolicitanteService {

    @PostMapping("api/solicitacao")
    SolicitarAnaliseResponse solicitarAnalise(@RequestBody SolicitarAnaliseRequest request);
}
