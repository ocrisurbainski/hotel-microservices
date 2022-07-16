package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetAllClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetClientByIdResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientController {

    Mono<ResponseEntity<CreateClientResponseDTO>> save(Mono<CreateClientRequestDTO> dto, UriComponentsBuilder componentsBuilder);

    Mono<ResponseEntity<GetClientByIdResponseDTO>> findById(String id);

    Flux<GetAllClientResponseDTO> findAll();

}
