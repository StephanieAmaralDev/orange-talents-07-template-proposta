package br.com.zup.edu.stephanie.propostas.service;

import br.com.zup.edu.stephanie.propostas.request.BloquearCartaoRequest;
import br.com.zup.edu.stephanie.propostas.response.BloquearCartaoResponse;
import br.com.zup.edu.stephanie.propostas.response.DadosCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name="cartoes", url = "http://localhost:8888")
public interface ConsultarCartaoService {

    @GetMapping("/api/cartoes")
    DadosCartaoResponse consultarCartaoByProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloquearCartaoResponse bloquearCartao(@PathVariable String id, @RequestBody @Valid BloquearCartaoRequest request);

}

