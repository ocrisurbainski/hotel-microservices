package com.urbainski.reservasapi.reservations.infra.mongo;

import com.urbainski.reservasapi.reservations.ReservationRepository;
import com.urbainski.reservasapi.reservations.domain.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class ReservationMongoRepository implements ReservationRepository {

    private final ReservationSpringRepository reservationSpringRepository;

    private final ReservationMongoMapper mapper;

    public ReservationMongoRepository(ReservationSpringRepository reservationSpringRepository, ReservationMongoMapper mapper) {
        this.reservationSpringRepository = reservationSpringRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        return Mono.just(reservation)
                .map(mapper::toReservationDocument)
                .flatMap(reservationSpringRepository::save)
                .map(mapper::toReservation)
                .doOnSuccess(value -> log.debug("Success when registering a new reservation, id: {}", value.getId()))
                .doOnError(throwable -> onErrorInsert(throwable, reservation));
    }

    private void onErrorInsert(Throwable throwable, Reservation reservation) {
        var logMessage = "Error registering new reservation: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(reservation, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

}
