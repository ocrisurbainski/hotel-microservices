package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import com.urbainski.reservasapi.reservations.domain.Reservation;
import com.urbainski.reservasapi.reservations.domain.ReservationStatus;
import com.urbainski.reservasapi.reservations.exception.ReservationStatusException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.urbainski.reservasapi.commons.message.SystemMessages.RESERVATION_STATUS_CANCELED_INVALID;
import static com.urbainski.reservasapi.commons.message.SystemMessages.RESERVATION_STATUS_IS_CANCELED;

@Service
public class ReservationFacade implements ReservationOperation {

    private final ReservationRepository repository;

    private final MessageSourceWrapperComponent messageSourceWrapperComponent;

    public ReservationFacade(ReservationRepository repository,
                             MessageSourceWrapperComponent messageSourceWrapperComponent) {
        this.repository = repository;
        this.messageSourceWrapperComponent = messageSourceWrapperComponent;
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
    public Mono<Reservation> findById(String id) {
        return repository.findById(id);
    }

    private Reservation validateCancelAction(Reservation reservation) {
        switch (reservation.getStatus()) {
            case RESERVED:
                break;
            case CANCELED:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_IS_CANCELED));
            default:
                throw ReservationStatusException.newInstanceWithStatusUnprocessableEntity(
                        messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_CANCELED_INVALID));
        }
        return reservation;
    }

}
