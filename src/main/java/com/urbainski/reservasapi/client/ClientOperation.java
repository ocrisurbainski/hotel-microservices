package com.urbainski.reservasapi.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ClientOperation {

    Mono<Client> save(Client client);

    Mono<Void> deleteById(String id);

    Mono<Client> findById(String id);

    Mono<Client> findByDocument(String document);

    Flux<Client> findByName(String name);

    Flux<Client> findAll();

}
