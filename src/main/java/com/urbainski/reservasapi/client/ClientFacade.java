package com.urbainski.reservasapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    @Override
    public Mono<Client> update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<Client> findById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Client> findByDocument(String document) {
        return clientRepository.findByDocument(document);
    }

    @Override
    public Flux<Client> findByTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }

    @Override
    public Flux<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

}
