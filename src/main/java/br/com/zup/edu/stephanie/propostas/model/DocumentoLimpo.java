package br.com.zup.edu.stephanie.propostas.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class DocumentoLimpo {
    private String documento;

    @Valid
    public DocumentoLimpo(@NotBlank String documento) {
        this.documento = documento;
    }

    public String documentoEncriptado() {
        return new BCryptPasswordEncoder().encode(documento);
    }
}
