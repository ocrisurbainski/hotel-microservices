package com.urbainski.reservasapi.reservations.infra.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSpringRepository extends ReactiveMongoRepository<ReservationDocument, String> {

}
