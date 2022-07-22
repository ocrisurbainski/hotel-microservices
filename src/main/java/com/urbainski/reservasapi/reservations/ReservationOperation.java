package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.reservations.domain.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationOperation {

    Mono<Reservation> save(Reservation reservation);

    Mono<Reservation> update(Reservation reservation);

    Mono<Reservation> cancel(String id);

    Mono<Reservation> checkin(String id);

    Mono<Reservation> findById(String id);

    Flux<Reservation> findAll();

}
