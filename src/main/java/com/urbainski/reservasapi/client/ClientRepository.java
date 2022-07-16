package com.urbainski.reservasapi.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientRepository {

    Mono<Client> save(Client client);

    Mono<Void> deleteById(String id);

    Mono<Client> findById(String id);

    Flux<Client> findAll();

}
