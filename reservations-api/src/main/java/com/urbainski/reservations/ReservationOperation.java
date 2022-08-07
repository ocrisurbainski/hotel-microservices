package com.urbainski.reservations;

import com.urbainski.reservations.domain.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationOperation {

    Mono<Reservation> save(Reservation reservation);

    Mono<Reservation> update(Reservation reservation);

    Mono<Reservation> cancel(String id);

    Mono<Reservation> checkin(String id);

    Mono<Reservation> checkout(String id);

    Mono<Reservation> findById(String id);

    Mono<Boolean> existsByGuestId(String guestId);

    Flux<Reservation> findByGuestDocument(String document);

    Flux<Reservation> findByGuestName(String name);

    Flux<Reservation> findByStatusCheckin();

    Flux<Reservation> findAll();

}
