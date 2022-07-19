package com.urbainski.reservasapi.customers.infra.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerSpringRepository extends ReactiveMongoRepository<CustomerDocument, String> {

    Mono<CustomerDocument> findByDocumentEquals(String document);

    Flux<CustomerDocument> findByTelephoneEquals(String telephone);

    Flux<CustomerDocument> findByNameContainingIgnoreCase(String name);

}
