package com.urbainski.reservasapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.AssertTrue;

@Service
public class ClientFacade implements ClientOperation {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientFacade(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @AssertTrue
    public Mono<Client> save(Client client) {
        return clientRepository.save(client);
    }

}
