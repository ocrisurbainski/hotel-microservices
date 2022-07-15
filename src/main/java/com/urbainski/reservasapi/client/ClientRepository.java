package com.urbainski.reservasapi.client;

import reactor.core.publisher.Mono;

public interface ClientRepository {

    Mono<Client> save(Client client);

}
