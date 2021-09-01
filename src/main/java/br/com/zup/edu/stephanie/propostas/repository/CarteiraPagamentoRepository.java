package br.com.zup.edu.stephanie.propostas.repository;

import br.com.zup.edu.stephanie.propostas.enums.GatwayPagamento;
import br.com.zup.edu.stephanie.propostas.model.CarteiraPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraPagamentoRepository extends JpaRepository<CarteiraPagamento, Long> {

    Optional<CarteiraPagamento> findByCarteiraAndCartaoId(GatwayPagamento carteira, String id);

}
