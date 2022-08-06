package com.urbainski.reservations;

import com.urbainski.reservations.domain.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationRepository {

    Mono<Reservation> save(Reservation reservation);

    Mono<Reservation> update(Reservation reservation);

    Mono<Reservation> cancel(Reservation reservation);

    Mono<Reservation> checkin(Reservation reservation);

    Mono<Reservation> checkout(Reservation reservation);

    Mono<Reservation> findById(String id);

    Flux<Reservation> findByGuestDocument(String document);

    Flux<Reservation> findByGuestName(String name);

    Flux<Reservation> findByStatusCheckin();

    Flux<Reservation> findAll();

}
