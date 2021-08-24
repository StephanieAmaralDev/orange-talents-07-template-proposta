package br.com.zup.edu.stephanie.propostas.repository;

import br.com.zup.edu.stephanie.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

        @Query("SELECT prop FROM Proposta prop " +
                "WHERE prop.statusProposta = br.com.zup.edu.stephanie.propostas.enums.StatusProposta.ELEGIVEL AND " +
                "(prop.numeroCartao = null OR prop.numeroCartao = '') ")
        Iterable<Proposta> findByElegivelESemCartao();
}
