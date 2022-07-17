package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.infra.controller.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientController {

    Mono<ResponseEntity<CreateClientResponseDTO>> save(Mono<CreateClientRequestDTO> dto, UriComponentsBuilder componentsBuilder);

    Mono<ResponseEntity<Void>> deleteById(String id);

    Mono<ResponseEntity<GetClientByIdResponseDTO>> findById(String id);

    Flux<GetClientByNameResponseDTO> findByName(String name);

    Flux<GetAllClientResponseDTO> findAll();

}
