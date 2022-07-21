package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.reservations.domain.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationRepository {

    Mono<Reservation> save(Reservation reservation);

    Mono<Reservation> update(Reservation reservation);

    Mono<Reservation> cancel(Reservation reservation);

    Mono<Reservation> checkin(Reservation reservation);

    Mono<Reservation> findById(String id);

    Flux<Reservation> findAll();

}
