package com.urbainski.reservasapi.client.infra.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientSpringRepository extends ReactiveMongoRepository<ClientDocument, String> {

    Mono<ClientDocument> findByDocumentEquals(String document);

    Flux<ClientDocument> findByNameContainingIgnoreCase(String name);

}
