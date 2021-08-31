package br.com.zup.edu.stephanie.propostas.repository;

import br.com.zup.edu.stephanie.propostas.model.BloqueioCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoBloqueiaRepository  extends JpaRepository<BloqueioCartao, Long> {
    Optional<BloqueioCartao> findByCartaoId(String id);
}
