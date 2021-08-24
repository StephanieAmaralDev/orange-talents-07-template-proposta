package br.com.zup.edu.stephanie.propostas.service;

import br.com.zup.edu.stephanie.propostas.response.DadosCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="cartoes", url = "http://localhost:8888")
public interface ConsultarCartaoService {

    @GetMapping("/api/cartoes")
    DadosCartaoResponse consultarCartaoByProposta(@RequestParam String idProposta);

}
