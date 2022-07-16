package com.urbainski.reservasapi.client;

import reactor.core.publisher.Mono;

public interface ClientOperation {

    Mono<Client> save(Client client);

    Mono<Client> findById(String id);

}
