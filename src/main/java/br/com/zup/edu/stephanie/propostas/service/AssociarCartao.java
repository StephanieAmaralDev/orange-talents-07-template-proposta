package br.com.zup.edu.stephanie.propostas.service;

import br.com.zup.edu.stephanie.propostas.enums.StatusProposta;
import br.com.zup.edu.stephanie.propostas.model.Cartao;
import br.com.zup.edu.stephanie.propostas.model.Proposta;
import br.com.zup.edu.stephanie.propostas.repository.CartaoRepository;
import br.com.zup.edu.stephanie.propostas.repository.PropostaRepository;
import br.com.zup.edu.stephanie.propostas.response.DadosCartaoResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AssociarCartao {

    private final ConsultarCartaoService consultarCartaoService;
    private final PropostaRepository propostaRepository;
    private final CartaoRepository cartaoRepository;
    private final Logger logger;

    public AssociarCartao(ConsultarCartaoService consultarCartaoClient, PropostaRepository propostaRepository, CartaoRepository cartaoRepository) {
        this.consultarCartaoService = consultarCartaoClient;
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
        this.logger = LoggerFactory.getLogger(AssociarCartao.class);
    }

    @Scheduled(cron = "0 */1 * ? * *") //1 minutos
    @Transactional
    public void associarCartaoEProposta() {
        logger.info("Verificando cartões...");

        List<Proposta> propostasSemCartao = propostaRepository.findByStatusPropostaAndCartaoIsNull(StatusProposta.ELEGIVEL);

        propostasSemCartao.forEach(p -> {
            Cartao cartao = consultarCartao(p.getId());
            if (Objects.nonNull(cartao)){
                cartao.atrelaProposta(p);
                p.atrelaCartao(cartao);
            }
        });
        propostaRepository.saveAll(propostasSemCartao);
    }

    private Cartao consultarCartao(Long id) {
        try{
            DadosCartaoResponse response = consultarCartaoService.consultarCartaoByProposta(String.valueOf(id));
            return response.toModel(propostaRepository);
        }catch (FeignException e) {
            return null;
        }
    }
}
