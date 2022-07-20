package com.urbainski.reservasapi.reservations;

import com.urbainski.reservasapi.commons.message.MessageSourceWrapperComponent;
import com.urbainski.reservasapi.commons.message.SystemMessages;
import com.urbainski.reservasapi.reservations.domain.Reservation;
import com.urbainski.reservasapi.reservations.exception.ReservationStatusException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.urbainski.reservasapi.commons.message.SystemMessages.RESERVATION_STATUS_INVALID;

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
                .map(value -> {
                    if (Objects.isNull(value.getStatus())) {
                        throw new ReservationStatusException(messageSourceWrapperComponent.getMessage(RESERVATION_STATUS_INVALID));
                    }
                    return value;
                })
                .map(repository::save)
                .flatMap(value -> value);
    }

}
