package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import com.urbainski.reservasapi.reservations.config.ReservationCheckinProperties;
import com.urbainski.reservasapi.reservations.domain.Reservation;
import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import com.urbainski.reservasapi.reservations.exception.ReservationCheckinException;
import com.urbainski.reservasapi.reservations.exception.ReservationStatusException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.urbainski.reservasapi.commons.message.SystemMessages.*;

@Service
public class ReservationFacade implements ReservationOperation {

    private final ReservationRepository repository;

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    private final ReservationCheckinProperties checkinProperties;

    public ReservationFacade(ReservationRepository repository,
                             MessageSourceWrapperComponent messageSourceWrapperComponent,
                             ReservationCheckinProperties checkinProperties) {
        this.repository = repository;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
        this.checkinProperties = checkinProperties;
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        return Mono.just(reservation)
                .doOnNext(value -> value.setStatus(ReservationStatus.RESERVED))
                .map(repository::save)
                .flatMap(value -> value);
    }

    @Override
    public Mono<Reservation> cancel(String id) {
        return repository.findById(id)
                .map(this::validateCancelAction)
                .doOnNext(value -> value.setStatus(ReservationStatus.CANCELED))
                .map(repository::cancel)
                .flatMap(value -> value);
    }

    @Override
    public Mono<Reservation> checkin(String id) {
        return repository.findById(id)
                .map(this::validateCheckinAction)
                .doOnNext(value -> {
                    value.setStatus(ReservationStatus.CHECKIN);
                    value.setDateCheckin(LocalDateTime.now());
                })
                .map(repository::checkin)
                .flatMap(value -> value);
    }

    @Override
    public Mono<Reservation> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Reservation> findAll() {
        return repository.findAll();
    }

    private Reservation validateCancelAction(Reservation reservation) {
        switch (reservation.getStatus()) {
            case RESERVED:
                break;
            case CANCELED:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CANCELED));
            default:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CANCELED_INVALID));
        }
        return reservation;
    }

    private Reservation validateCheckinAction(Reservation reservation) {
        switch (reservation.getStatus()) {
            case CANCELED:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CANCELED));
            case CHECKIN:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CHECKIN));
            case CHECKOUT:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CHECKOUT));
        }

        var dateNow = LocalDate.now();
        if (dateNow.isBefore(reservation.getDateReservationInitial())) {
            throw ReservationCheckinException.newInstanceWithStatusUnprocessableEntity(
                    messageSourceWrapperComponent.getMessage(RESERVATION_CHECKIN_DATE_DOES_NOT_ALLOW, new Object[]{reservation.getDateReservationInitial()}));
        }

        var timeNow = LocalTime.now();
        if (timeNow.isBefore(checkinProperties.getHourInit())) {
            throw ReservationCheckinException.newInstanceWithStatusUnprocessableEntity(
                    messageSourceWrapperComponent.getMessage(RESERVATION_CHECKIN_TIME_DOES_NOT_ALLOW, new Object[]{checkinProperties.getHourInit()}));
        }

        return reservation;
    }

}
