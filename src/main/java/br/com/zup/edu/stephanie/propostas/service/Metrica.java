package br.com.zup.edu.stephanie.propostas.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class Metrica {

    private final MeterRegistry registry;

    public Metrica(MeterRegistry registry) {
        this.registry = registry;
    }
}
