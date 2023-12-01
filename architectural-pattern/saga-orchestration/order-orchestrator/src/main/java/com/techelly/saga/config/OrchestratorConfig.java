package com.techelly.saga.config;

import com.techelly.saga.service.OrchestratorService;
import com.techelly.dto.OrchestratorRequestDTO;
import com.techelly.dto.OrchestratorResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class OrchestratorConfig {

    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> processor(){
        return flux -> flux
                            .flatMap(dto -> this.orchestratorService.orderProduct(dto))
                            .doOnNext(dto -> System.out.println("Status : " + dto.getStatus()));
    }

}
