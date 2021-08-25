package br.com.zup.edu.stephanie.propostas.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

        @Id
        private String id;

        private LocalDateTime emitidoEm;

        private String titular;

        private BigDecimal limite;

        @OneToOne(mappedBy = "cartao", fetch = FetchType.EAGER)
        private Proposta proposta;


        private Cartao() {}

        public Cartao(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, Proposta proposta) {
            this.id = id;
            this.emitidoEm = emitidoEm;
            this.titular = titular;
            this.limite = limite;
            this.proposta = proposta;
        }

        public String getId() {
            return id;
        }

        public LocalDateTime getEmitidoEm() {
            return emitidoEm;
        }

        public String getTitular() {
            return titular;
        }

        public BigDecimal getLimite() {
            return limite;
        }

        public Proposta getProposta() {
            return proposta;
        }

        public void atrelaProposta(Proposta proposta){
            this.proposta = proposta;
        }
    }
