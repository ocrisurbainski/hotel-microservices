package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ClientController {

    ResponseEntity<CreateClientResponseDTO> save(CreateClientRequestDTO dto, UriComponentsBuilder componentsBuilder);

}
