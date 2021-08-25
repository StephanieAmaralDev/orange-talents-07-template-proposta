package br.com.zup.edu.stephanie.propostas.repository;

import br.com.zup.edu.stephanie.propostas.enums.StatusProposta;
import br.com.zup.edu.stephanie.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    List<Proposta> findByStatusPropostaAndCartaoIsNull(StatusProposta status);
}
