package com.urbainski.reservasapi.client.infra.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ClientSpringRepository extends ReactiveMongoRepository<ClientDocument, String> {

    Flux<ClientDocument> findByNameContainingIgnoreCase(String name);

}
