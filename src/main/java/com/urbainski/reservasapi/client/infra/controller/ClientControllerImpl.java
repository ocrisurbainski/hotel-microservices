package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.ClientOperation;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetAllClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetClientByIdResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
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

        return dto.map(mapper::toClient)
                .flatMap(clientOperation::save)
                .map(mapper::toCreateClientResponseDTO)
                .map(value -> {
                    var uri = componentsBuilder.path("/clients/{id}").buildAndExpand(value.getId()).toUri();
                    return ResponseEntity.created(uri).body(value);
                })
                .log();
    }

    @Override
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return clientOperation.deleteById(id).map(ResponseEntity::ok).log();
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GetClientByIdResponseDTO>> findById(@PathVariable String id) {
        return clientOperation.findById(id)
                .map(mapper::toGetClientByIdResponseDTO)
                .map(ResponseEntity::ok)
                .log();
    }

    @Override
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetAllClientResponseDTO> findAll() {
        return this.clientOperation.findAll()
                .map(mapper::toGetAllClientResponseDTO)
                .log();
    }

}
