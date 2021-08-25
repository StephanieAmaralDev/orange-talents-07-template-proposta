package br.com.zup.edu.stephanie.propostas.repository;

import br.com.zup.edu.stephanie.propostas.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Optional<Cartao> findById(String id);
}
