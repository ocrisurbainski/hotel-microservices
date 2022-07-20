package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.reservations.domain.Reservation;
import reactor.core.publisher.Mono;

public interface ReservationOperation {

    Mono<Reservation> save(Reservation reservation);

}
