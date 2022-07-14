package com.urbainski.reservasapi.client.infra.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSpringRepository extends MongoRepository<ClientDocument, String> {

}
