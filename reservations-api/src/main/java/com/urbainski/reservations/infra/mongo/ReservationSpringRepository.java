package com.urbainski.reservations.infra.mongo;

import com.urbainski.reservations.domain.ReservationStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReservationSpringRepository extends ReactiveMongoRepository<ReservationDocument, String> {

    Mono<Boolean> existsByGuestId(String guestId);

    Flux<ReservationDocument> findByGuestDocument(String document);

    Flux<ReservationDocument> findByGuestNameContainingIgnoreCase(String name);

    Flux<ReservationDocument> findByStatusEquals(ReservationStatus status);

}
