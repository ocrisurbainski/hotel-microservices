package com.urbainski.reservasapi.client.infra.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSpringRepository extends ReactiveMongoRepository<ClientDocument, String> {

}
