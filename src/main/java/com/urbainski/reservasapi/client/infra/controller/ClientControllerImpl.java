package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.ClientOperation;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<CreateClientResponseDTO> save(
            @RequestBody @Valid CreateClientRequestDTO dto, UriComponentsBuilder componentsBuilder) {

        var client = clientOperation.save(mapper.toClient(dto));
        var responseDto = mapper.toCreateClientResponseDTO(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        //var uri = componentsBuilder.path("/clients/{id}").buildAndExpand(responseDto.getId()).toUri();
        //return ResponseEntity.created(uri).body(responseDto);
    }

}
