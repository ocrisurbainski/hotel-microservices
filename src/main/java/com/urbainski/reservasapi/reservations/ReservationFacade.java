package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import com.urbainski.reservasapi.reservations.calculate.service.ReservationCalculateOperation;
import com.urbainski.reservasapi.reservations.domain.Reservation;
import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import com.urbainski.reservasapi.reservations.exception.ReservationCheckinException;
import com.urbainski.reservasapi.reservations.exception.ReservationException;
import com.urbainski.reservasapi.reservations.exception.ReservationStatusException;
import com.urbainski.reservasapi.reservations.properties.ReservationCheckinProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.urbainski.reservasapi.commons.message.SystemMessages.*;

@Service
public class ReservationFacade implements ReservationOperation {

    private final ReservationRepository repository;

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    private final ReservationCheckinProperties checkinProperties;

    private final ReservationCalculateOperation reservationCalculateOperation;

    public ReservationFacade(ReservationRepository repository,
                             MessageSourceWrapperComponent messageSourceWrapperComponent,
                             ReservationCheckinProperties checkinProperties,
                             ReservationCalculateOperation reservationCalculateOperation) {
        this.repository = repository;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
        this.checkinProperties = checkinProperties;
        this.reservationCalculateOperation = reservationCalculateOperation;
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        return Mono.just(reservation)
                .map(this::validateSaveAction)
                .doOnNext(value -> {
                    value.setStatus(ReservationStatus.RESERVED);
                    value.setAmount(BigDecimal.ZERO);
                })
                .flatMap(repository::save);
    }

    @Override
    public Mono<Reservation> update(Reservation reservation) {
        return repository.findById(reservation.getId())
                .map(value -> {
                    reservation.setStatus(value.getStatus());
                    return reservation;
                })
                .map(this::validateUpdateAction)
                .flatMap(repository::update);

    }

    @Override
    public Mono<Reservation> cancel(String id) {
        return repository.findById(id)
                .map(this::validateCancelAction)
                .doOnNext(value -> value.setStatus(ReservationStatus.CANCELED))
                .flatMap(repository::cancel);
    }

    @Override
    public Mono<Reservation> checkin(String id) {
        return repository.findById(id)
                .map(this::validateCheckinAction)
                .doOnNext(value -> {
                    value.setStatus(ReservationStatus.CHECKIN);
                    value.setDateCheckin(LocalDateTime.now());
                })
                .flatMap(repository::checkin);
    }

    @Override
    public Mono<Reservation> checkout(String id) {
        return repository.findById(id)
                .map(this::validateCheckoutAction)
                .doOnNext(value -> {
                    value.setStatus(ReservationStatus.CHECKOUT);
                    value.setDateCheckout(LocalDateTime.now());

                    reservationCalculateOperation.calculate(value);
                })
                .flatMap(repository::checkout);
    }

    @Override
    public Mono<Reservation> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Reservation> findByGuestDocument(String document) {
        return repository.findByGuestDocument(document);
    }

    @Override
    public Flux<Reservation> findByGuestName(String name) {
        return repository.findByGuestName(name);
    }

    @Override
    public Flux<Reservation> findByStatusCheckin() {
        return repository.findByStatusCheckin();
    }

    @Override
    public Flux<Reservation> findAll() {
        return repository.findAll();
    }

    private Reservation validateSaveAction(Reservation reservation) {
        if (reservation.getDateReservationFinish().isBefore(reservation.getDateReservationInitial())) {
            throw ReservationException.newInstanceWithStatusUnprocessableEntity(
                    messageSourceWrapperComponent.getMessage(RESERVATION_DATES_INVALID));
        }
        return reservation;
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
        validateReservationStatusIsReserved(reservation);

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

    private Reservation validateCheckoutAction(Reservation reservation) {
        switch (reservation.getStatus()) {
            case RESERVED:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_RESERVED));
            case CANCELED:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CANCELED));
            case CHECKOUT:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CHECKOUT));
        }
        return reservation;
    }

    private Reservation validateUpdateAction(Reservation reservation) {

        validateReservationStatusIsReserved(reservation);
        return validateSaveAction(reservation);
    }

    private void validateReservationStatusIsReserved(Reservation reservation) {
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
    }

}
