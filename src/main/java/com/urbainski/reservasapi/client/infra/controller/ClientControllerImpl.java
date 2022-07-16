package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.ClientOperation;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetClientByIdResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientControllerImpl implements ClientController {

    private final ClientOperation clientOperation;

    private final ClientControllerMapper mapper;

    @Autowired
    public ClientControllerImpl(ClientOperation clientOperation, ClientControllerMapper mapper) {
        this.clientOperation = clientOperation;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public Mono<ResponseEntity<CreateClientResponseDTO>> save(
            @RequestBody @Valid Mono<CreateClientRequestDTO> dto, UriComponentsBuilder componentsBuilder) {

        //var uri = componentsBuilder.path("/clients/{id}").buildAndExpand(responseDto.getId()).toUri();
        //return ResponseEntity.created(uri).body(responseDto);
        return dto.map(mapper::toClient)
                .flatMap(clientOperation::save)
                .map(mapper::toCreateClientResponseDTO)
                .map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value))
                .log();
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GetClientByIdResponseDTO>> findById(@PathVariable String id) {
        return clientOperation.findById(id)
                .map(mapper::toGetClientByIdResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

}
