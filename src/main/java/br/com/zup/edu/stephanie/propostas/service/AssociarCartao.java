package br.com.zup.edu.stephanie.propostas.service;

import br.com.zup.edu.stephanie.propostas.model.Proposta;
import br.com.zup.edu.stephanie.propostas.repository.PropostaRepository;
import br.com.zup.edu.stephanie.propostas.response.DadosCartaoResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AssociarCartao {

    private final ConsultarCartaoService consultarCartaoService;
    private final PropostaRepository propostaRepository;
    private final Logger logger;

    public AssociarCartao(ConsultarCartaoService consultarCartaoService, PropostaRepository propostaRepository) {
        this.consultarCartaoService = consultarCartaoService;
        this.propostaRepository = propostaRepository;
        this.logger = LoggerFactory.getLogger(AssociarCartao.class);
    }

    @Scheduled(cron = "0 */30 * ? * *")
    public void associarCartaoEProposta() {
        logger.info("Verificando cartões...");

        Iterable<Proposta> propostasSemCartao = propostaRepository.findByElegivelESemCartao();

        propostasSemCartao.forEach(prop -> {
            String cartao = consultarCartao(prop.getId());
            if (cartao != null)
                prop.associarCartao(cartao);
        });

        propostaRepository.saveAll(propostasSemCartao);
    }

    private String consultarCartao(Long id) {
        try{
            DadosCartaoResponse response = consultarCartaoService.consultarCartaoByProposta(String.valueOf(id));
            return response.getId();
        }catch (FeignException e) {
            return null;
        }
    }
}
