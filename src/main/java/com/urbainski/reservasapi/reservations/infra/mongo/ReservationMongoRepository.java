package com.urbainski.reservasapi.reservations.infra.mongo;

import com.urbainski.reservasapi.commons.exception.NotFoundException;
import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import com.urbainski.reservasapi.reservations.ReservationRepository;
import com.urbainski.reservasapi.reservations.domain.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.urbainski.reservasapi.commons.message.SystemMessages.RESERVATION_NOT_FOUND;

@Slf4j
@Component
public class ReservationMongoRepository implements ReservationRepository {

    private final ReservationSpringRepository reservationSpringRepository;

    private final ReservationMongoMapper mapper;

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    public ReservationMongoRepository(
            ReservationSpringRepository reservationSpringRepository,
            ReservationMongoMapper mapper,
            MessageSourceWrapperComponent messageSourceWrapperComponent) {
        this.reservationSpringRepository = reservationSpringRepository;
        this.mapper = mapper;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
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

    @Override
    public Mono<Reservation> update(Reservation reservation) {
        return Mono.just(reservation)
                .map(mapper::toReservationDocument)
                .flatMap(reservationSpringRepository::save)
                .map(mapper::toReservation)
                .doOnSuccess(value -> log.debug("Success when updating a reservation, id: {}", value.getId()))
                .doOnError(throwable -> onErrorUpdate(throwable, reservation));
    }

    @Override
    public Mono<Reservation> cancel(Reservation reservation) {
        return update(reservation);
    }

    @Override
    public Mono<Reservation> checkin(Reservation reservation) {
        return update(reservation);
    }

    @Override
    public Mono<Reservation> findById(String id) {
        return reservationSpringRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(messageSourceWrapperComponent.getMessage(RESERVATION_NOT_FOUND))))
                .map(mapper::toReservation)
                .doOnError(throwable -> log.error("Error in method ::findById::", throwable.getCause()))
                .doOnSuccess(value -> log.debug("Success in method ::findById:: {}", value));
    }

    @Override
    public Flux<Reservation> findAll() {
        return reservationSpringRepository.findAll().map(mapper::toReservation);
    }

    private void onErrorInsert(Throwable throwable, Reservation reservation) {
        var logMessage = "Error registering new reservation: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(reservation, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

    private void onErrorUpdate(Throwable throwable, Reservation reservation) {
        var logMessage = "Error updating reservation: {}, cause: {}";
        var json = ToStringBuilder.reflectionToString(reservation, ToStringStyle.JSON_STYLE);
        log.error(logMessage, json, throwable.getMessage());
    }

}
